package edu.towson.cosc457.CarDealership.exceptions;

import edu.towson.cosc457.CarDealership.model.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(NotFoundException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorDto.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(value = AlreadyAssignedException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AlreadyAssignedException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorDto.builder().message(exception.getMessage()).build());
    }
}
