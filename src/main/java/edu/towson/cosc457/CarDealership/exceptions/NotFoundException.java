package edu.towson.cosc457.CarDealership.exceptions;

import java.text.MessageFormat;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final String entity, final Long entityId) {
        super(MessageFormat.format("Could not find {0} with id: {1}", entity, entityId));
    }
}
