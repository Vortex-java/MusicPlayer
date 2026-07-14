package app.vx.musicplayer.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(

        @NotBlank(message = "login is empty")
        @Size(max = 50)
        String login,

        @Size(min = 8)
        @NotBlank(message = "password is empty")
        String password
) {
}
