package app.vx.musicplayer.cover.controller;

import app.vx.musicplayer.cover.dto.CreateCoverRequest;
import app.vx.musicplayer.cover.service.CoverService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/covers")
public class CoverController {

    private final CoverService coverService;

    public CoverController (CoverService coverService) {
        this.coverService = coverService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create (
            @RequestPart("file")MultipartFile file,
            @Valid @RequestPart("request") CreateCoverRequest request
    ) {
        coverService.create(file, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<Resource> getFile (@PathVariable Long id) {

        Resource resource = coverService.getFile(id);

        MediaType mediaType = MediaTypeFactory
                .getMediaType(resource).
                orElse(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok().contentType(mediaType).body(resource);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/file")
    public ResponseEntity<Void> deleteFile (@PathVariable Long id) {
        coverService.delete(id);
        return ResponseEntity.noContent().build();
    }
}