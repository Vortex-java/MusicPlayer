package app.vx.musicplayer.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteArtistRepository extends JpaRepository<FavoriteArtistRepository, Long> {
}
