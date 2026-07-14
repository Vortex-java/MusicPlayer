package app.vx.musicplayer.favorite.repository;

import app.vx.musicplayer.favorite.entity.FavoriteAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteAlbumRepository extends JpaRepository<FavoriteAlbum, Long> {
}
