package dk.nine.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({CustomExceptions.RequestNumberOneException.class})
    public ResponseEntity<Object> handleNumberOne(CustomExceptions.RequestNumberOneException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({CustomExceptions.RequestNumberTwoException.class})
    public ResponseEntity<Object> handleNumberTwo(CustomExceptions.RequestNumberTwoException exception) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(exception.getMessage());
    }

    @ExceptionHandler({CustomExceptions.RequestNumberThreeException.class})
    public ResponseEntity<Object> handleNumberThree(CustomExceptions.RequestNumberThreeException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400
                .body(exception.getMessage());
    }

    @ExceptionHandler({CustomExceptions.RequestNumberFourException.class})
    public ResponseEntity<Object> handleNumberFour(CustomExceptions.RequestNumberFourException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED) // 401
                .body(exception.getMessage());
    }

    @ExceptionHandler({CustomExceptions.RequestNumberFiveException.class})
    public ResponseEntity<Object> handleNumberFive(CustomExceptions.RequestNumberFiveException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN) // 403
                .body(exception.getMessage());
    }

    @ExceptionHandler({CustomExceptions.RequestNumberSixException.class})
    public ResponseEntity<Object> handleNumberSix(CustomExceptions.RequestNumberSixException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 404
                .body(exception.getMessage());
    }

    @ExceptionHandler({CustomExceptions.RequestNumberSevenException.class})
    public ResponseEntity<Object> handleNumberSeven(CustomExceptions.RequestNumberSevenException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409
                .body(exception.getMessage());
    }

    @ExceptionHandler({CustomExceptions.RequestNumberEightException.class})
    public ResponseEntity<Object> handleNumberEight(CustomExceptions.RequestNumberEightException exception) {
        return ResponseEntity
                .status(HttpStatus.GONE) // 410
                .body(exception.getMessage());
    }

    @ExceptionHandler({CustomExceptions.RequestNumberNineException.class})
    public ResponseEntity<Object> handleNumberNine(CustomExceptions.RequestNumberNineException exception) {
        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS) // 429
                .body(exception.getMessage());
    }


}
