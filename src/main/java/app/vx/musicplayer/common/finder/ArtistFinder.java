package app.vx.musicplayer.common.finder;

import app.vx.musicplayer.artist.entity.Artist;
import app.vx.musicplayer.artist.repository.ArtistRepository;
import app.vx.musicplayer.exception.ArtistNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Artist> findTop4ByOrderByIdDesc() {
        return artistRepository.findAll(
                PageRequest.of(
                        0,
                        4,
                        Sort.by(Sort.Direction.DESC, "id")
                )
        ).toList();
    }
}
