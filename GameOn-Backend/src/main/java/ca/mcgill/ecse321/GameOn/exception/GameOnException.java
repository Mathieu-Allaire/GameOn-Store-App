package ca.mcgill.ecse321.GameOn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class GameOnException extends RuntimeException {

    @NonNull
    private HttpStatus status;

    public GameOnException(@NonNull HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    @NonNull
    public HttpStatus getStatus() {
        return status;
    }
}
