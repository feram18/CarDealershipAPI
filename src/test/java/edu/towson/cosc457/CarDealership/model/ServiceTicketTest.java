package edu.towson.cosc457.CarDealership.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceTicketTest {
    private ServiceTicket serviceTicket;
    private Comment comment;

    @BeforeEach
    void setUp() {
        serviceTicket = ServiceTicket.builder()
                .id(1L)
                .comments(new ArrayList<>())
                .build();

        comment = Comment.builder()
                .id(1L)
                .build();
    }

    @Test
    void shouldAddComment() {
        serviceTicket.addComment(comment);

        assertThat(serviceTicket.getComments()).usingRecursiveComparison()
                .isEqualTo(Collections.singletonList(comment));
    }

    @Test
    void shouldRemoveComment() {
        serviceTicket.addComment(comment);
        serviceTicket.removeComment(comment);

        assertThat(serviceTicket.getComments().isEmpty()).isTrue();
    }
}
