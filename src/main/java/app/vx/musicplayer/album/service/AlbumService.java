package app.vx.musicplayer.album.service;

import app.vx.musicplayer.album.dto.*;
import app.vx.musicplayer.album.entity.Album;
import app.vx.musicplayer.album.mapper.AlbumMapper;
import app.vx.musicplayer.album.mapper.TrackMapper;
import app.vx.musicplayer.album.repository.AlbumRepository;
import app.vx.musicplayer.artist.repository.ArtistRepository;
import app.vx.musicplayer.common.dto.PageResponse;
import app.vx.musicplayer.common.finder.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    private final AlbumFinder albumFinder;
    private final ArtistFinder artistFinder;
    private final CoverFinder coverFinder;
    private final TrackFinder trackFinder;
    private final CoverUrlFinder coverUrlFinder;

    private final AlbumMapper albumMapper;
    private final TrackMapper trackMapper;

    public AlbumService (
            AlbumRepository albumRepository,
            ArtistRepository artistRepository,
            AlbumMapper albumMapper,
            AlbumFinder albumFinder,
            ArtistFinder artistFinder,
            CoverFinder coverFinder,
            TrackMapper trackMapper,
            TrackFinder trackFinder,
            CoverUrlFinder coverUrlFinder
    ) {
        this.albumRepository = albumRepository;
        this.albumFinder = albumFinder;
        this.artistFinder = artistFinder;
        this.coverFinder = coverFinder;
        this.albumMapper = albumMapper;
        this.trackMapper = trackMapper;
        this.trackFinder = trackFinder;
        this.coverUrlFinder = coverUrlFinder;
    }

    @Transactional
    public void create (CreateAlbumRequest request) {

        Album album = new Album(
                request.name(),
                artistFinder.findByIdOrElseThrow(request.artistId()),
                request.releaseDate(),
                coverFinder.findByIdOrElseNull(request.coverId())
        );

        albumRepository.save(album);
    }

    @Transactional
    public void change (ChangeAlbumRequest request, Long id) {

        Album album = albumFinder.findByIdOrElseThrow(id);

        album.setName(request.name());
        album.setArtist(artistFinder.findByIdOrElseThrow(request.artistId()));
        album.setReleaseDate(request.releaseDate());
        album.setCover(coverFinder.findByIdOrElseNull(request.coverId()));
    }

    public PageResponse<GetPreviewAlbumResponse> getAll (Pageable pageable) {
        Page<GetPreviewAlbumResponse> page = albumRepository
                .findAll(pageable)
                .map(albumMapper::toPreviewResponse);

        return PageResponse.from(page);
    }

    public GetAlbumPageResponse getAlbum (Long id) {
        Album album = albumFinder.findByIdOrElseThrow(id);

        return new GetAlbumPageResponse(
                album.getId(),
                album.getName(),
                album.getArtist().getId(),
                album.getArtist().getName(),
                album.getReleaseDate(),
                coverUrlFinder.findUrl(album.getCover()),
                trackFinder.findByAlbumId(id)
                        .stream()
                        .map(trackMapper::toResponse)
                        .toList()
        );
    }

    @Transactional
    public void delete (Long id) {
        Album album = albumFinder.findByIdOrElseThrow(id);
        albumRepository.delete(album);
    }
}
