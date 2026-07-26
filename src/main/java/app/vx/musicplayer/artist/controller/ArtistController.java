package app.vx.musicplayer.artist.controller;

import app.vx.musicplayer.album.dto.GetPreviewAlbumResponse;
import app.vx.musicplayer.artist.dto.*;
import app.vx.musicplayer.artist.service.ArtistService;
import app.vx.musicplayer.common.dto.PageResponse;
import app.vx.musicplayer.track.dto.GetTrackResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<GetArtistPageResponse> getArtist (@PathVariable Long id) {
        return ResponseEntity.ok(artistService.getArtist(id));
    }

    @GetMapping("/{id}/albums")
    public ResponseEntity<PageResponse<GetPreviewAlbumResponse>> getAlbums (
            @PathVariable Long id,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(artistService.getAlbums(id, pageable));
    }

    @GetMapping("/{id}/tracks")
    public ResponseEntity<PageResponse<GetTrackResponse>> getTracks (
            @PathVariable Long id,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(artistService.getTracks(id, pageable));
    }
}
