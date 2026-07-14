package app.vx.musicplayer.artist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateArtistRequest(

        @NotBlank(message = "artist name is empty")
        @Size(max = 100)
        String name
) {
}
