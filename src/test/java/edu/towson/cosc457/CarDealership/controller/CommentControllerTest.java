package edu.towson.cosc457.CarDealership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.towson.cosc457.CarDealership.controller.CommentController;
import edu.towson.cosc457.CarDealership.model.Comment;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CommentControllerTest {
    public MockMvc mockMvc;
    @Autowired
    public CommentController commentController;
    @MockBean
    public CommentService commentService;
    private final ObjectMapper mapper = new ObjectMapper();
    private Comment comment;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

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
    }

    @Test
    void shouldAddComment() throws Exception {
        when(commentService.addComment(comment)).thenReturn(comment);

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(comment)))
                .andExpect(status().is(201));
    }

    @Test
    void shouldGetAllComments() throws Exception {
        when(commentService.getComments()).thenReturn(Collections.singletonList(comment));

        mockMvc.perform(get("/api/v1/comments"))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetCommentById() throws Exception {
        when(commentService.getComment(comment.getId())).thenReturn(comment);

        mockMvc.perform(get("/api/v1/comments/{id}", comment.getId()))
                .andExpect(status().is(200));
    }

    @Test
    void shouldDeleteComment() throws Exception {
        when(commentService.deleteComment(comment.getId())).thenReturn(comment);

        mockMvc.perform(delete("/api/v1/comments/{id}", comment.getId()))
                .andExpect(status().is(200));
    }

    @Test
    void shouldUpdateComment() throws Exception {
        Comment editedComment = Comment.builder()
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
        when(commentService.editComment(comment.getId(), editedComment)).thenReturn(editedComment);

        mockMvc.perform(put("/api/v1/comments/{id}", comment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedComment)))
                .andExpect(status().is(200));
    }
}
