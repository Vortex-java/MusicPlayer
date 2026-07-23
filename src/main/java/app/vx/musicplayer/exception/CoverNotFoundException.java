package app.vx.musicplayer.exception;

import org.springframework.http.HttpStatus;

public class CoverNotFoundException extends ApiException {
    public CoverNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
