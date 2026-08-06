package app.vx.musicplayer.artist.controller;

import app.vx.musicplayer.artist.dto.*;
import app.vx.musicplayer.artist.service.ArtistService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController (ArtistService artistService) {
        this.artistService = artistService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create (
            @Valid @RequestPart("request") CreateArtistRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        artistService.create(request, file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> change (
            @PathVariable Long id,
            @Valid @RequestBody ChangeArtistRequest request
    ) {
        artistService.change(id, request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/imageUrl")
    public ResponseEntity<Void> changeImageUrl (
            @PathVariable Long id,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        artistService.changeImageUrl(id, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetArtistPageResponse> getArtist (@PathVariable Long id) {
        return ResponseEntity.ok(artistService.getArtist(id));
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<Resource> getFile (@PathVariable Long id) {

        Resource resource = artistService.getFile(id);

        MediaType mediaType = MediaTypeFactory
                .getMediaType(resource).
                orElse(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok().contentType(mediaType).body(resource);
    }

    @GetMapping("/{id}/albums")
    public ResponseEntity<GetArtistPageAlbumsResponse> getAlbumsPage(
            @PathVariable Long id,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(artistService.getAlbums(id, pageable));
    }

    @GetMapping("/{id}/tracks")
    public ResponseEntity<GetArtistPageTracksResponse> getTracksPage(
            @PathVariable Long id,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(artistService.getTracks(id, pageable));
    }
}
