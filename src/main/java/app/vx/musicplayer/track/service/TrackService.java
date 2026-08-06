package app.vx.musicplayer.track.service;

import app.vx.musicplayer.album.entity.Album;
import app.vx.musicplayer.track.mapper.TrackMapper;
import app.vx.musicplayer.artist.entity.Artist;
import app.vx.musicplayer.common.dto.PageResponse;
import app.vx.musicplayer.common.event.FileCleanupEvent;
import app.vx.musicplayer.common.event.FileDeleteEvent;
import app.vx.musicplayer.common.finder.AlbumFinder;
import app.vx.musicplayer.common.finder.ArtistFinder;
import app.vx.musicplayer.common.finder.CoverFinder;
import app.vx.musicplayer.common.finder.TrackFinder;
import app.vx.musicplayer.cover.entity.Cover;
import app.vx.musicplayer.exception.InvalidFileException;
import app.vx.musicplayer.storage.AudioMetadataService;
import app.vx.musicplayer.storage.FileStorageService;
import app.vx.musicplayer.storage.entity.Filetype;
import app.vx.musicplayer.track.dto.ChangeTrackRequest;
import app.vx.musicplayer.track.dto.CreateTrackRequest;
import app.vx.musicplayer.track.dto.GetTrackDetailsResponse;
import app.vx.musicplayer.track.dto.GetTrackResponse;
import app.vx.musicplayer.track.entity.Track;
import app.vx.musicplayer.track.repository.TrackRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
public class TrackService {

    private final TrackRepository trackRepository;
    private final FileStorageService fileStorageService;
    private final AudioMetadataService audioMetadataService;

    private final AlbumFinder albumFinder;
    private final ArtistFinder artistFinder;
    private final CoverFinder coverFinder;
    private final TrackFinder trackFinder;

    private final TrackMapper trackMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    private static final String TRACKS_DIRECTORY = "songs";
    private static final Filetype FILE_TYPE = Filetype.AUDIO;

    private final Set<String> allowedTypes = Set.of(
            "audio/mpeg",
            "audio/ogg"
    );

    private static final String LYRICS_DIRECTORY = "lyrics";
    private static final Filetype LYRICS_FILE_TYPE = Filetype.LYRICS;

    public TrackService (
            TrackRepository trackRepository,
            FileStorageService fileStorageService,
            AlbumFinder albumFinder,
            ArtistFinder artistFinder,
            CoverFinder coverFinder,
            AudioMetadataService audioMetadataService,
            TrackFinder trackFinder,
            ApplicationEventPublisher applicationEventPublisher,
            TrackMapper trackMapper
    ) {
        this.trackRepository = trackRepository;
        this.fileStorageService = fileStorageService;
        this.albumFinder = albumFinder;
        this.artistFinder = artistFinder;
        this.coverFinder = coverFinder;
        this.audioMetadataService = audioMetadataService;
        this.trackFinder = trackFinder;
        this.applicationEventPublisher = applicationEventPublisher;
        this.trackMapper = trackMapper;
    }

    @Transactional
    public void create (MultipartFile trackFile, MultipartFile lyricsFile, CreateTrackRequest request) {

        Artist artist = artistFinder.findByIdOrElseThrow(request.artistId());

        Album album = albumFinder.findByIdOrElseNull(request.albumId());

        Cover cover = coverFinder.findByIdOrElseNull(request.coverId());

        String trackPath = fileStorageService.save(trackFile, TRACKS_DIRECTORY, FILE_TYPE, allowedTypes);

        String lyricsPath = null;

        if (lyricsFile != null && !lyricsFile.isEmpty()) {
            lyricsPath = fileStorageService.save(lyricsFile, LYRICS_DIRECTORY, LYRICS_FILE_TYPE, null);
        }

        try {
            long duration = audioMetadataService.getDuration(trackPath);

            Track track = new Track(
                    request.name(),
                    album,
                    artist,
                    duration,
                    trackPath,
                    lyricsPath,
                    cover,
                    request.trackNumber()
            );

            trackRepository.save(track);
        } catch (InvalidFileException e) {
            applicationEventPublisher.publishEvent(
                    new FileCleanupEvent(trackPath)
            );

            if (lyricsPath != null) {
                applicationEventPublisher.publishEvent(
                        new FileCleanupEvent(lyricsPath)
                );
            }

            throw e;
        }
    }

    public PageResponse<GetTrackResponse> getAll (Pageable pageable) {
        Page<GetTrackResponse> page = trackRepository.findAll(pageable).map(trackMapper::toResponse);

        return PageResponse.from(page);
    }

    public GetTrackDetailsResponse getTrackDetailsResponse (Long id) {
        Track track = trackFinder.findByIdOrElseThrow(id);

        return trackMapper.toDetails(track);
    }

    public Resource stream (Long id) {
        Track track = trackFinder.findByIdOrElseThrow(id);

        return fileStorageService.load(track.getFilePath());
    }

    public Resource getLyrics (Long id) {
        Track track = trackFinder.findByIdOrElseThrow(id);

        return fileStorageService.load(track.getLyricsPath());
    }

    @Transactional
    public void change (Long id, ChangeTrackRequest request) {
        Track track = trackFinder.findByIdOrElseThrow(id);

        Artist artist = artistFinder.findByIdOrElseThrow(request.artistId());

        Album album = albumFinder.findByIdOrElseNull(request.albumId());

        Cover cover = coverFinder.findByIdOrElseNull(request.coverId());

        track.setName(request.name());
        track.setArtist(artist);
        track.setAlbum(album);
        track.setCover(cover);
        track.setTrackNumber(request.trackNumber());
    }

    @Transactional
    public void changeLyrics (Long id, MultipartFile file) {

        Track track = trackFinder.findByIdOrElseThrow(id);
        String oldLyrics = track.getLyricsPath();

        if (file != null && !file.isEmpty()) {

            oldLyrics = track.getLyricsPath();

            String newLyrics = fileStorageService.save(file, LYRICS_DIRECTORY, LYRICS_FILE_TYPE, null);

            track.setLyricsPath(newLyrics);

            if (oldLyrics != null) {
                applicationEventPublisher.publishEvent(
                        new FileDeleteEvent(oldLyrics)
                );
            }
        } else {
            if (oldLyrics != null) {
                applicationEventPublisher.publishEvent(
                        new FileDeleteEvent(oldLyrics)
                );
            }
        }
    }

    @Transactional
    public void delete (Long id) {
        Track track = trackFinder.findByIdOrElseThrow(id);

        trackRepository.delete(track);

        applicationEventPublisher.publishEvent(
                new FileDeleteEvent(track.getFilePath())
        );
    }
}
