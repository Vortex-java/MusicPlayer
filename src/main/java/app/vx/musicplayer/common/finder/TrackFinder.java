package app.vx.musicplayer.common.finder;

import app.vx.musicplayer.exception.TrackNotFoundException;
import app.vx.musicplayer.track.entity.Track;
import app.vx.musicplayer.track.repository.TrackRepository;
import org.springframework.stereotype.Service;

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
}
