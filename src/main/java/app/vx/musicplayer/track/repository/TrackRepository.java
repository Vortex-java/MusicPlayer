package app.vx.musicplayer.track.repository;

import app.vx.musicplayer.track.entity.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

    List<Track> findByAlbumId(Long albumId);

    Page<Track> findByArtistId(Long artistId, Pageable pageable);

    Page<Track> findByArtistIdOrderByIdDesc (Long artistId, Pageable pageable);
}
