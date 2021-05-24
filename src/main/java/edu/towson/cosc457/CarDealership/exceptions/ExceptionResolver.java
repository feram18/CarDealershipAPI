package edu.towson.cosc457.CarDealership.exceptions;

import edu.towson.cosc457.CarDealership.model.dto.ErrorDto;
import edu.towson.cosc457.CarDealership.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionResolver.class);

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(final NotFoundException exception) {
        LOGGER.error("Unable to find entity.", exception);
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorDto.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(value = AlreadyAssignedException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(final AlreadyAssignedException exception) {
        LOGGER.error("Unable to assign entity.", exception);
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorDto.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(final NoHandlerFoundException exception) {
        LOGGER.error("Invalid endpoint.", exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(final AlreadyExistsException exception) {
        LOGGER.error("Data conflict. Entity with identifier already exists.", exception);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorDto.builder().message(exception.getMessage()).build());
    }
}
