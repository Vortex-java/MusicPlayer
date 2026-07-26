package app.vx.musicplayer.album.controller;

import app.vx.musicplayer.album.dto.*;
import app.vx.musicplayer.album.service.AlbumService;
import app.vx.musicplayer.common.dto.PageResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController (AlbumService albumService) {
        this.albumService = albumService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create (@Valid @RequestBody CreateAlbumRequest request) {
        albumService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> change (
            @Valid @RequestBody ChangeAlbumRequest request,
            @PathVariable Long id
    ) {
        albumService.change(request, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<GetPreviewAlbumResponse>> getAll (@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(albumService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAlbumPageResponse> getAlbum (@PathVariable Long id) {
        return ResponseEntity.ok(albumService.getAlbum(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        albumService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
