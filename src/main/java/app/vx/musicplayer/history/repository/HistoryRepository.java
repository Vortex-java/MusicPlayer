package app.vx.musicplayer.history.repository;

import app.vx.musicplayer.history.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
}
