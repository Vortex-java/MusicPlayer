package app.vx.musicplayer.exception;

import org.springframework.http.HttpStatus;

public class TrackNotFoundException extends ApiException {
    public TrackNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
