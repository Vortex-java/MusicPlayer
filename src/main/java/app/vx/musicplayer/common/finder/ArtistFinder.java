package app.vx.musicplayer.common.finder;

import app.vx.musicplayer.artist.entity.Artist;
import app.vx.musicplayer.artist.repository.ArtistRepository;
import app.vx.musicplayer.exception.ArtistNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ArtistFinder {

    private final ArtistRepository artistRepository;

    public ArtistFinder(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Artist findByIdOrElseThrow(Long id) {
        return artistRepository.findById(id).orElseThrow(
                () -> new ArtistNotFoundException("Artist not found")
        );
    }

    public void checkExists (Long id) {
        if (!artistRepository.existsById(id)) {
            throw new ArtistNotFoundException("Artist not found");
        }
    }
}
