package alekssandher.board.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import alekssandher.board.exception.dto.ErrorDetails;
import alekssandher.board.exception.dto.ErrorResponses.*;

import alekssandher.board.exception.exceptions.Exceptions.*;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CardExceptionHandler
{
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(NotFoundException ex, HttpServletRequest request)
    {
        ErrorDetails error = new NotFound(request, "Not Found", ex.getMessage());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(BadRequestException ex, HttpServletRequest request)
    {
        ErrorDetails error = new BadRequest(request, "Bad Request", ex.getMessage());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(InternalErrorException ex, HttpServletRequest request)
    {
        ErrorDetails error = new InternalErrorCustom(request);
        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
