package app.vx.musicplayer.track.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChangeTrackRequest(

        @NotBlank(message = "track name is empty")
        @Size(max = 100)
        String name,

        @NotNull(message = "artist id is empty")
        Long artistId,

        Long albumId,
        Long coverId
) {
}
