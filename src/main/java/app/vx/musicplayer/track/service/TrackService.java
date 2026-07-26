package app.vx.musicplayer.track.service;

import app.vx.musicplayer.album.entity.Album;
import app.vx.musicplayer.artist.entity.Artist;
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
import app.vx.musicplayer.track.entity.Track;
import app.vx.musicplayer.track.repository.TrackRepository;
import org.springframework.context.ApplicationEventPublisher;
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

    private final ApplicationEventPublisher applicationEventPublisher;

    private static final String TRACKS_DIRECTORY = "songs";
    private static final Filetype FILE_TYPE = Filetype.AUDIO;

    private final Set<String> allowedTypes = Set.of(
            "audio/mpeg",
            "audio/ogg"
    );

    public TrackService (
            TrackRepository trackRepository,
            FileStorageService fileStorageService,
            AlbumFinder albumFinder,
            ArtistFinder artistFinder,
            CoverFinder coverFinder,
            AudioMetadataService audioMetadataService,
            TrackFinder trackFinder,
            ApplicationEventPublisher applicationEventPublisher
    ) {
        this.trackRepository = trackRepository;
        this.fileStorageService = fileStorageService;
        this.albumFinder = albumFinder;
        this.artistFinder = artistFinder;
        this.coverFinder = coverFinder;
        this.audioMetadataService = audioMetadataService;
        this.trackFinder = trackFinder;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public void create (MultipartFile file, CreateTrackRequest request) {

        Artist artist = artistFinder.findByIdOrElseThrow(request.artistId());

        Album album = albumFinder.findByIdOrElseNull(request.albumId());

        Cover cover = coverFinder.findByIdOrElseNull(request.coverId());

        String path = fileStorageService.save(file, TRACKS_DIRECTORY, FILE_TYPE, allowedTypes);

        try {
            long duration = audioMetadataService.getDuration(path);

            Track track = new Track(
                    request.name(),
                    album,
                    artist,
                    duration,
                    path,
                    cover
            );

            trackRepository.save(track);
        } catch (InvalidFileException e) {
            applicationEventPublisher.publishEvent(
                    new FileCleanupEvent(path)
            );

            throw e;
        }
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
