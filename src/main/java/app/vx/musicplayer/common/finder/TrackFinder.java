package app.vx.musicplayer.common.finder;

import app.vx.musicplayer.exception.TrackNotFoundException;
import app.vx.musicplayer.track.entity.Track;
import app.vx.musicplayer.track.repository.TrackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackFinder {

    private final TrackRepository trackRepository;

    public TrackFinder (TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public Track findByIdOrElseThrow (Long id) {
        return trackRepository.findById(id).orElseThrow(
                () -> new TrackNotFoundException("Track not found")
        );
    }

    public List<Track> findByAlbumId (Long id) {
        return trackRepository.findByAlbumId(id);
    }

    public List<Track> findByAlbumIdOrderByTrackNumberAsc(Long id) {
        return trackRepository.findByAlbumIdOrderByTrackNumberAsc(id);
    }

    public Page<Track> findByArtistId (Long id, Pageable pageable) {
        return trackRepository.findByArtistId(id, pageable);
    }

    public List<Track> findLatestByArtistId (Long artistId, int limits) {
        return trackRepository.findByArtistIdOrderByIdDesc(
                artistId,
                PageRequest.of(0, limits)
        ).getContent();
    }

    public List<Track> findTop10ByOrderByIdDesc () {
        return trackRepository.findAll(
                PageRequest.of(
                        0,
                        10,
                        Sort.by(Sort.Direction.DESC, "id")
                )
        ).toList();
    }
}
