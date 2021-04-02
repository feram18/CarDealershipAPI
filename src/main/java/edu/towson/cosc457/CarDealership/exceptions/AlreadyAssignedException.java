package edu.towson.cosc457.CarDealership.exceptions;

import java.text.MessageFormat;

public class AlreadyAssignedException extends RuntimeException {
    public AlreadyAssignedException(final String entityA, final Long idA, final String entityB, final Long idB) {
        super(MessageFormat.format("{0} id: {1} is already assigned to {2} id: {3}", entityA, idA, entityB, idB));
    }
}
