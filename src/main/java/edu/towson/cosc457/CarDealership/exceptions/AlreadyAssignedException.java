package edu.towson.cosc457.CarDealership.exceptions;

import java.text.MessageFormat;

public class AlreadyAssignedException extends RuntimeException {
    public AlreadyAssignedException(final String ownedEntity, final Long ownedId, final String ownerEntity, final Long ownerId) {
        super(MessageFormat.format("{0} id: {1} is already assigned to {2} id: {3}", ownedEntity, ownedId, ownerEntity, ownerId));
    }
}
