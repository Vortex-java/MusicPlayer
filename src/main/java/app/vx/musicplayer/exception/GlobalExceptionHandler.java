package app.vx.musicplayer.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException (ApiException exception) {
        ErrorResponse response = new ErrorResponse(
                exception.getMessage(),
                exception.getStatus().value(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(exception.getStatus()).body(response);
    }
}