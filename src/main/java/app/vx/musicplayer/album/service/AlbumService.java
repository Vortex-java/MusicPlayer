package app.vx.musicplayer.album.service;

import app.vx.musicplayer.album.dto.ChangeAlbumRequest;
import app.vx.musicplayer.album.dto.CreateAlbumRequest;
import app.vx.musicplayer.album.dto.GetPreviewAlbumResponse;
import app.vx.musicplayer.album.entity.Album;
import app.vx.musicplayer.album.mapper.AlbumMapper;
import app.vx.musicplayer.album.repository.AlbumRepository;
import app.vx.musicplayer.artist.repository.ArtistRepository;
import app.vx.musicplayer.common.finder.AlbumFinder;
import app.vx.musicplayer.common.finder.ArtistFinder;
import app.vx.musicplayer.common.finder.CoverFinder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final AlbumFinder albumFinder;
    private final ArtistFinder artistFinder;
    private final CoverFinder coverFinder;

    public AlbumService (AlbumRepository albumRepository, ArtistRepository artistRepository, AlbumMapper albumMapper, AlbumFinder albumFinder, ArtistFinder artistFinder, CoverFinder coverFinder) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
        this.albumFinder = albumFinder;
        this.artistFinder = artistFinder;
        this.coverFinder = coverFinder;
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

    public Page<GetPreviewAlbumResponse> getAll (Pageable pageable) {
        return albumRepository.findAll(pageable).map(albumMapper::toPreviewResponse);
    }

    public GetPreviewAlbumResponse getPreview (Long id) {
        Album album = albumFinder.findByIdOrElseThrow(id);

        return albumMapper.toPreviewResponse(album);
    }

    @Transactional
    public void delete (Long id) {
        Album album = albumFinder.findByIdOrElseThrow(id);
        albumRepository.delete(album);
    }
}
