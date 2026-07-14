package app.vx.musicplayer.exception;

import java.time.LocalDateTime;

public record ErrorResponse (
        String message,
        int status,
        LocalDateTime timestamp
) {
}
