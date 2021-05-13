package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.model.Comment;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Captor
    private ArgumentCaptor<Comment> commentArgumentCaptor;
    private Comment comment;
    private Comment editedComment;

    @BeforeEach
    public void setUp() {
        comment = Comment.builder()
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

        editedComment = Comment.builder()
                .id(1L)
                .serviceTicket(ServiceTicket.builder()
                        .id(1L)
                        .build())
                .mechanic(Mechanic.builder()
                        .id(1L)
                        .build())
                .dateCreated(LocalDate.now())
                .content("New Content")
                .build();
    }

    @Test
    void shouldSaveComment() {
        commentService.addComment(comment);

        verify(commentRepository, times(1)).save(commentArgumentCaptor.capture());

        assertThat(commentArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(comment);
    }

    @Test
    void shouldFailToSaveNullComment() {
        commentService.addComment(null);

        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void shouldGetCommentById() {
        Mockito.when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        Comment actualComment = commentService.getComment(comment.getId());
        verify(commentRepository, times(1)).findById(comment.getId());

        assertAll(() -> {
            assertThat(actualComment).isNotNull();
            assertThat(actualComment).usingRecursiveComparison().isEqualTo(comment);
        });
    }

    @Test
    void shouldFailToGetCommentById() {
        Mockito.when(commentRepository.findById(comment.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> commentService.getComment(comment.getId()));

        verify(commentRepository, times(1)).findById(comment.getId());
    }

    @Test
    void shouldGetAllComments() {
        List<Comment> expectedComments = new ArrayList<>();
        expectedComments.add(comment);
        expectedComments.add(Comment.builder()
                .id(2L)
                .build());
        expectedComments.add(Comment.builder()
                .id(3L)
                .build());

        Mockito.when(commentRepository.findAll()).thenReturn(expectedComments);
        List<Comment> actualComments = commentService.getComments();
        verify(commentRepository, times(1)).findAll();

        assertAll(() -> {
            assertThat(actualComments).isNotNull();
            assertThat(actualComments.size()).isEqualTo(expectedComments.size());
        });
    }

    @Test
    void shouldGetAllComments_EmptyList() {
        Mockito.when(commentRepository.findAll()).thenReturn(new ArrayList<>());

        List<Comment> actualComments = commentService.getComments();

        assertThat(actualComments).isEmpty();
    }

    @Test
    void shouldDeleteComment() {
        Mockito.when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        Comment deletedComment = commentService.deleteComment(comment.getId());
        verify(commentRepository, times(1)).delete(comment);

        assertAll(() -> {
            assertThat(deletedComment).isNotNull();
            assertThat(deletedComment).usingRecursiveComparison().isEqualTo(comment);
        });
    }

    @Test
    void shouldFailToDeleteComment() {
        Mockito.when(commentRepository.findById(comment.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> commentService.deleteComment(comment.getId()));

        verify(commentRepository, never()).delete(any(Comment.class));
    }

    @Test
    void shouldUpdateComment() {
        Mockito.when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        Comment updatedComment = commentService.editComment(comment.getId(), editedComment);

        assertAll(() -> {
            assertThat(updatedComment).isNotNull();
            assertThat(updatedComment).usingRecursiveComparison().isEqualTo(editedComment);
        });
    }

    @Test
    void shouldFailToUpdateComment() {
        Mockito.when(commentRepository.findById(comment.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> commentService.editComment(comment.getId(), editedComment));
    }
}
