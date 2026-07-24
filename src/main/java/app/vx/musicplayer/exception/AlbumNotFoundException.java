package app.vx.musicplayer.exception;

import org.springframework.http.HttpStatus;

public class AlbumNotFoundException extends ApiException {
    public AlbumNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
