package app.vx.musicplayer.artist.service;

import app.vx.musicplayer.artist.dto.ChangeArtistRequest;
import app.vx.musicplayer.artist.dto.CreateArtistRequest;
import app.vx.musicplayer.artist.dto.GetArtistResponse;
import app.vx.musicplayer.artist.entity.Artist;
import app.vx.musicplayer.artist.repository.ArtistRepository;
import app.vx.musicplayer.exception.ArtistAlreadyExistsException;
import app.vx.musicplayer.exception.ArtistNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService (ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public void create (CreateArtistRequest request) {
        if (artistRepository.existsByName(request.name())) {
            throw new ArtistAlreadyExistsException("Artist already exists");
        }

        Artist newArtist = new Artist(request.name());

        artistRepository.save(newArtist);
    }

    @Transactional
    public void change (Long id, ChangeArtistRequest request) {

        Artist artist = artistRepository.findById(id)
                .orElseThrow(
                        () -> new ArtistNotFoundException("Artist not found")
                );

        Artist artist1 = artistRepository.findByName(request.name());

        if (artist1 != null && !Objects.equals(artist.getId(), artist1.getId())) {
            throw new ArtistAlreadyExistsException("Artist already exists");
        }

        artist.setName(request.name());
    }

    public GetArtistResponse getArtist (Long id) {

        Artist artist = artistRepository.findById(id)
                .orElseThrow(
                        () -> new ArtistNotFoundException("Artist not found")
                );

        return new GetArtistResponse(artist.getName());
    }
}
