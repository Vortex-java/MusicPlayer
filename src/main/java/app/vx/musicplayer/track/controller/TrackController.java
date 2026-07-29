package app.vx.musicplayer.track.controller;

import app.vx.musicplayer.common.dto.PageResponse;
import app.vx.musicplayer.track.dto.*;
import app.vx.musicplayer.track.service.TrackService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {

    private final TrackService trackService;

    public TrackController (TrackService trackService) {
        this.trackService = trackService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create (
            @RequestPart("file") MultipartFile file,
            @Valid @RequestPart("request") CreateTrackRequest request)
    {
        trackService.create(file, request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> change (
            @PathVariable Long id,
            @Valid @RequestBody ChangeTrackRequest request
    ) {
        trackService.change(id, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<GetTrackResponse>> getAll (@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(trackService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTrackDetailsResponse> getTrackDetails (@PathVariable Long id) {
        return ResponseEntity.ok(trackService.getTrackDetailsResponse(id));
    }

    @GetMapping("/{id}/stream")
    public ResponseEntity<ResourceRegion> stream (
            @PathVariable Long id,
            @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {
        Resource resource = trackService.stream(id);

        System.out.println("Range = " + rangeHeader);

        long contentLength = resource.contentLength();

        ResourceRegion resourceRegion;

        HttpStatus status;

        if (rangeHeader == null) {
            resourceRegion = new ResourceRegion(resource, 0, contentLength);
            status = HttpStatus.OK;
        } else {
            HttpRange range = HttpRange
                    .parseRanges(rangeHeader)
                    .getFirst();

            resourceRegion = range.toResourceRegion(resource);
            status = HttpStatus.PARTIAL_CONTENT;
        }

        MediaType mediaType = MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.status(
                status
        )
                .contentType(mediaType)
                .body(resourceRegion);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        trackService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
