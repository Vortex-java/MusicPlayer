package app.vx.musicplayer.exception;

public class ArtistAlreadyExistsException extends RuntimeException {
    public ArtistAlreadyExistsException(String message) {
        super(message);
    }
}
