package edu.towson.cosc457.CarDealership.exceptions;

import edu.towson.cosc457.CarDealership.model.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionResolver {
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(final NotFoundException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorDto.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(value = AlreadyAssignedException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(final AlreadyAssignedException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorDto.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(final NoHandlerFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(final AlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorDto.builder().message(exception.getMessage()).build());
    }
}
