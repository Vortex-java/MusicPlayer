package app.vx.musicplayer.exception;

import org.springframework.http.HttpStatus;

public class ArtistAlreadyExistsException extends ApiException{
    public ArtistAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
