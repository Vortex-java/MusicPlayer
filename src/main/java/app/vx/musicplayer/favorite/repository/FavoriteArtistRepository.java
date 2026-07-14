package app.vx.musicplayer.favorite.repository;

import app.vx.musicplayer.favorite.entity.FavoriteArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteArtistRepository extends JpaRepository<FavoriteArtist, Long> {
}
