package alekssandher.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import alekssandher.board.exception.dtos.*;

@RestControllerAdvice
public class CardExceptionHandler
{
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleCustomException(EntityNotFoundException ex)
    {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CardFinishedException.class)
    public ResponseEntity<String> handleCustomException(CardFinishedException ex)
    {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
