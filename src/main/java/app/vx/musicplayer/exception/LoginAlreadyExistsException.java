package app.vx.musicplayer.exception;

import org.springframework.http.HttpStatus;

public class LoginAlreadyExistsException extends ApiException {
    public LoginAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
