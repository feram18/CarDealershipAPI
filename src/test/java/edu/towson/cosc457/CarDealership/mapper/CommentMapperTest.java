package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.model.Comment;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.model.dto.CommentDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentMapperTest {
    private static CommentMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = new CommentMapperImpl();
    }

    @Test
    void shouldMapToDto() {
        Comment comment = Comment.builder()
                .id(1L)
                .serviceTicket(ServiceTicket.builder()
                        .id(1L)
                        .build())
                .mechanic(Mechanic.builder()
                        .id(1L)
                        .build())
                .dateCreated(LocalDate.now())
                .content("Content")
                .build();

        CommentDto commentDto = mapper.toDto(comment);

        assertAll(() -> {
           assertThat(commentDto).isInstanceOf(CommentDto.class);
           assertEquals(commentDto.getId(), comment.getId());
           assertEquals(commentDto.getServiceTicketId(), comment.getServiceTicket().getId());
           assertEquals(commentDto.getMechanicId(), comment.getMechanic().getId());
           assertEquals(commentDto.getDateCreated(), comment.getDateCreated());
           assertEquals(commentDto.getContent(), comment.getContent());
        });
    }

    @Test
    void shouldMapFromDto() {
        CommentDto commentDto = CommentDto.builder()
                .id(1L)
                .serviceTicketId(1L)
                .mechanicId(1L)
                .dateCreated(LocalDate.now())
                .content("Content")
                .build();

        Comment comment = mapper.fromDto(commentDto);

        assertAll(() -> {
            assertThat(comment).isInstanceOf(Comment.class);
            assertEquals(comment.getId(), commentDto.getId());
            assertEquals(comment.getDateCreated(), commentDto.getDateCreated());
            assertEquals(comment.getContent(), commentDto.getContent());
        });
    }

    @Test
    void shouldReturnNullEntity() {
        Comment comment = mapper.fromDto(null);

        assertThat(comment).isEqualTo(null);
    }

    @Test
    void shouldReturnNullDto() {
        CommentDto commentDto = mapper.toDto(null);

        assertThat(commentDto).isEqualTo(null);
    }
}
