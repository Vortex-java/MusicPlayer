package app.vx.musicplayer.album.repository;

import app.vx.musicplayer.album.entity.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Page<Album> findByArtistId(Long artistId, Pageable pageable);

    Page<Album> findByArtistIdOrderByIdDesc (Long artistId, Pageable pageable);
}
