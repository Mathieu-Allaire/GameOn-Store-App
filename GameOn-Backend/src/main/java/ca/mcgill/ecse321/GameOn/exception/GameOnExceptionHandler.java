package ca.mcgill.ecse321.GameOn.exception;

import ca.mcgill.ecse321.GameOn.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GameOnExceptionHandler {
    @ExceptionHandler(GameOnException.class)
    public ResponseEntity<ErrorDto> handleGameOnException(GameOnException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), e.getStatus());
    }
}
