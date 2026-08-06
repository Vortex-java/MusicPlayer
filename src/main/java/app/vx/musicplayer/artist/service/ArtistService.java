package app.vx.musicplayer.artist.service;

import app.vx.musicplayer.album.dto.GetPreviewAlbumResponse;
import app.vx.musicplayer.album.entity.Album;
import app.vx.musicplayer.album.mapper.AlbumMapper;
import app.vx.musicplayer.common.event.FileDeleteEvent;
import app.vx.musicplayer.storage.FileStorageService;
import app.vx.musicplayer.storage.entity.Filetype;
import app.vx.musicplayer.track.mapper.TrackMapper;
import app.vx.musicplayer.artist.dto.*;
import app.vx.musicplayer.artist.entity.Artist;
import app.vx.musicplayer.artist.repository.ArtistRepository;
import app.vx.musicplayer.common.dto.PageResponse;
import app.vx.musicplayer.common.finder.AlbumFinder;
import app.vx.musicplayer.common.finder.ArtistFinder;
import app.vx.musicplayer.common.finder.TrackFinder;
import app.vx.musicplayer.exception.ArtistAlreadyExistsException;
import app.vx.musicplayer.track.dto.GetTrackResponse;
import app.vx.musicplayer.track.entity.Track;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistFinder artistfinder;
    private final AlbumFinder albumFinder;
    private final TrackFinder trackFinder;

    private final AlbumMapper albumMapper;
    private final TrackMapper trackMapper;

    private final FileStorageService fileStorageService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private static final String ARTIST_IMAGE_DIRECTORY = "artist_images";
    private static final Filetype FILE_TYPE = Filetype.IMAGE_ARTIST;

    private final Set<String> allowedTypes = Set.of(
            "image/png",
            "image/jpeg",
            "image/webp"
    );

    public ArtistService (
            ArtistRepository artistRepository,
            ArtistFinder artistfinder,
            AlbumFinder albumFinder,
            AlbumMapper albumMapper,
            TrackFinder trackFinder,
            TrackMapper trackMapper,
            FileStorageService fileStorageService,
            ApplicationEventPublisher applicationEventPublisher) {
        this.artistRepository = artistRepository;
        this.artistfinder = artistfinder;
        this.albumFinder = albumFinder;
        this.albumMapper = albumMapper;
        this.trackFinder = trackFinder;
        this.trackMapper = trackMapper;
        this.fileStorageService = fileStorageService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public void create (CreateArtistRequest request, MultipartFile file) {

        if (artistRepository.existsByName(request.name())) {
            throw new ArtistAlreadyExistsException("Artist already exists");
        }

        String path = null;

        if (file != null && !file.isEmpty()) {
            path = fileStorageService.save(
                    file,
                    ARTIST_IMAGE_DIRECTORY,
                    FILE_TYPE,
                    allowedTypes
            );
        }

        Artist newArtist = new Artist(request.name(), path);

        artistRepository.save(newArtist);
    }

    @Transactional
    public void change (Long id, ChangeArtistRequest request) {

        Artist artist = artistfinder.findByIdOrElseThrow(id);
        Artist artist1 = artistRepository.findByName(request.name());

        if (artist1 != null && !Objects.equals(artist.getId(), artist1.getId())) {
            throw new ArtistAlreadyExistsException("Artist already exists");
        }

        artist.setName(request.name());
    }

    @Transactional
    public void changeImageUrl (Long id, MultipartFile file) {
        Artist artist = artistfinder.findByIdOrElseThrow(id);

        String oldImage = artist.getImageUrl();

        if (file != null && !file.isEmpty()) {

            String newImage = fileStorageService.save(
                    file,
                    ARTIST_IMAGE_DIRECTORY,
                    FILE_TYPE,
                    allowedTypes
            );

            artist.setImageUrl(newImage);
        }

        if (oldImage != null) {
            applicationEventPublisher.publishEvent(
                    new FileDeleteEvent(oldImage));
        }
    }

    public Resource getFile (Long id) {
        Artist artist = artistfinder.findByIdOrElseThrow(id);

        return fileStorageService.load(artist.getImageUrl());
    }

    public GetArtistPageResponse getArtist (Long id) {

        Artist artist = artistfinder.findByIdOrElseThrow(id);

        List<GetTrackResponse> popularTracks = trackFinder
                .findLatestByArtistId(id, 10)
                .stream()
                .map(trackMapper::toResponse)
                .toList();

        List<GetPreviewAlbumResponse> albums = albumFinder
                .findLatestByArtistId(id, 5)
                .stream()
                .map(albumMapper::toPreviewResponse)
                .toList();

        return new GetArtistPageResponse(
                artist.getName(),
                popularTracks,
                albums
        );
    }

    public GetArtistPageAlbumsResponse getAlbums (Long id, Pageable pageable) {

        Artist artist = artistfinder.findByIdOrElseThrow(id);

        Page<Album> albums = albumFinder.findByArtistId(id, pageable);

        return new GetArtistPageAlbumsResponse(
                artist.getName(),
                PageResponse.from(
                        albums.map(
                                albumMapper::toPreviewResponse
                        )
                )
        );
    }

    public GetArtistPageTracksResponse getTracks (Long id, Pageable pageable) {
        Artist artist = artistfinder.findByIdOrElseThrow(id);

        Page<Track> tracks = trackFinder.findByArtistId(id, pageable);

        return new GetArtistPageTracksResponse(
                artist.getName(),
                PageResponse.from(
                        tracks.map(
                                trackMapper::toResponse
                        )
                )
        );
    }
}
