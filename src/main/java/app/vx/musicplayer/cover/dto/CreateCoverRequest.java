package app.vx.musicplayer.cover.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCoverRequest(

        @NotBlank(message = "cover name is empty")
        @Size(max = 75)
        String name
) {
}
