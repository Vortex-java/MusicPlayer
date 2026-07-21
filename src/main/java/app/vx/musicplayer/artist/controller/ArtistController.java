package app.vx.musicplayer.artist.controller;

import app.vx.musicplayer.artist.dto.ChangeArtistRequest;
import app.vx.musicplayer.artist.dto.CreateArtistRequest;
import app.vx.musicplayer.artist.dto.GetArtistResponse;
import app.vx.musicplayer.artist.service.ArtistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController (ArtistService artistService) {
        this.artistService = artistService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create (@Valid @RequestBody CreateArtistRequest request) {
        artistService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> change (
            @PathVariable Long id,
            @Valid @RequestBody ChangeArtistRequest request) {
        artistService.change(id, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetArtistResponse> getArtist (@PathVariable Long id) {
        return ResponseEntity.ok(artistService.getArtist(id));
    }
}
