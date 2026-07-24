package app.vx.musicplayer.album.service;

import app.vx.musicplayer.album.dto.ChangeAlbumRequest;
import app.vx.musicplayer.album.dto.CreateAlbumRequest;
import app.vx.musicplayer.album.dto.GetPreviewAlbumResponse;
import app.vx.musicplayer.album.entity.Album;
import app.vx.musicplayer.album.mapper.AlbumMapper;
import app.vx.musicplayer.album.repository.AlbumRepository;
import app.vx.musicplayer.artist.entity.Artist;
import app.vx.musicplayer.artist.repository.ArtistRepository;
import app.vx.musicplayer.cover.entity.Cover;
import app.vx.musicplayer.cover.repository.CoverRepository;
import app.vx.musicplayer.exception.AlbumNotFoundException;
import app.vx.musicplayer.exception.ArtistNotFoundException;
import app.vx.musicplayer.exception.CoverNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final CoverRepository coverRepository;
    private final AlbumMapper albumMapper;

    public AlbumService (AlbumRepository albumRepository, ArtistRepository artistRepository, CoverRepository coverRepository, AlbumMapper albumMapper) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.coverRepository = coverRepository;
        this.albumMapper = albumMapper;
    }

    @Transactional
    public void create (CreateAlbumRequest request) {

        Album album = new Album(
                request.name(),
                getArtist(request.artistId()),
                request.releaseDate(),
                getCover(request.coverId())
        );

        albumRepository.save(album);
    }

    @Transactional
    public void change (ChangeAlbumRequest request, Long id) {

        Album album = getAlbum(id);

        album.setName(request.name());
        album.setArtist(getArtist(request.artistId()));
        album.setReleaseDate(request.releaseDate());
        album.setCover(getCover(request.coverId()));
    }

    public Page<GetPreviewAlbumResponse> getAll (Pageable pageable) {
        return albumRepository.findAll(pageable).map(albumMapper::toPreviewResponse);
    }

    public GetPreviewAlbumResponse getPreview (Long id) {
        Album album = getAlbum(id);

        return albumMapper.toPreviewResponse(album);
    }

    @Transactional
    public void delete (Long id) {
        Album album = getAlbum(id);
        albumRepository.delete(album);
    }

    private Album getAlbum (Long id) {
        return albumRepository.findById(id).orElseThrow(
                () -> new AlbumNotFoundException("Album not found")
        );
    }

    private Artist getArtist (Long id) {
        return artistRepository.findById(id).orElseThrow(
                () -> new ArtistNotFoundException("Artist not found")
        );
    }

    private Cover getCover (Long id) {
        if (id == null) {
            return null;
        }

        return coverRepository.findById(id)
                .orElseThrow(() -> new CoverNotFoundException("Cover not found"));
    }
}
