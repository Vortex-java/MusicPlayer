package app.vx.musicplayer.common.finder;

import app.vx.musicplayer.album.entity.Album;
import app.vx.musicplayer.album.repository.AlbumRepository;
import app.vx.musicplayer.exception.AlbumNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumFinder {

    private final AlbumRepository albumRepository;

    public AlbumFinder (AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public Album findByIdOrElseThrow(Long id) {
        return albumRepository.findById(id).orElseThrow(
                () -> new AlbumNotFoundException("Album not found")
        );
    }

    public Album findByIdOrElseNull(Long id) {
        return albumRepository.findById(id).orElse(null);
    }

    public Page<Album> findByArtistId (Long id, Pageable pageable) {
        return albumRepository.findByArtistId(id, pageable);
    }

    public List<Album> findLatestByArtistId (Long artistId, int limit) {
        return albumRepository.findByArtistIdOrderByIdDesc(
                artistId,
                PageRequest.of(0, limit)
        ).getContent();
    }
}
