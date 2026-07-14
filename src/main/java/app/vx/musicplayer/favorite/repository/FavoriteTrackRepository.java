package app.vx.musicplayer.favorite.repository;

import app.vx.musicplayer.favorite.entity.FavoriteTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteTrackRepository extends JpaRepository<FavoriteTrack, Long> {
}
