package app.vx.musicplayer.common.finder;

import app.vx.musicplayer.album.entity.Album;
import app.vx.musicplayer.album.repository.AlbumRepository;
import app.vx.musicplayer.exception.AlbumNotFoundException;
import org.springframework.stereotype.Service;

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
}
