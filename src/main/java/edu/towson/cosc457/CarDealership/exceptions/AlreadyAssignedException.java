package edu.towson.cosc457.CarDealership.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity conflict") // 409 ERROR
public class AlreadyAssignedException extends RuntimeException {
    private final HttpStatus status;

    public AlreadyAssignedException(final String ownedEntity,
                                    final Long ownedId,
                                    final String ownerEntity,
                                    final Long ownerId,
                                    final HttpStatus status) {
        super(MessageFormat.format(
                "{0} id: {1} is already assigned to {2} id: {3}",
                ownedEntity,
                ownedId,
                ownerEntity,
                ownerId)
        );
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
