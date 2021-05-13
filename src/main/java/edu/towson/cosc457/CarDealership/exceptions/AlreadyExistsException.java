package edu.towson.cosc457.CarDealership.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity conflict") // 409 ERROR
public class AlreadyExistsException extends RuntimeException {
    private final HttpStatus status;

    public AlreadyExistsException(final String entity,
                                  final String attribute,
                                  final Long id,
                                  final HttpStatus status) {
        super(MessageFormat.format(
                "{0} with {1}: {2} already exists.",
                entity,
                attribute,
                id)
        );
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
