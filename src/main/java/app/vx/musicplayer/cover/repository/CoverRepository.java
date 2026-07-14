package app.vx.musicplayer.cover.repository;

import app.vx.musicplayer.cover.entity.Cover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverRepository extends JpaRepository<Cover, Long> {
}
