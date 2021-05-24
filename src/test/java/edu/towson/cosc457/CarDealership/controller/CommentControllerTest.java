package edu.towson.cosc457.CarDealership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.model.Comment;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    private Comment editedComment;

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
    void shouldAddComment() throws Exception {
        when(commentService.addComment(comment)).thenReturn(comment);

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(comment)))
                .andExpect(status().is(201));
    }

    @Test
    void shouldFailToSaveComment_Null() throws Exception {
        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(null)))
                .andExpect(status().is(400)); // BAD_REQUEST
    }

    @Test
    void shouldGetAllComments() throws Exception {
        when(commentService.getComments()).thenReturn(Collections.singletonList(comment));

        mockMvc.perform(get("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetAllComments_EmptyList() throws Exception {
        when(commentService.getComments()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    void shouldGetCommentById() throws Exception {
        when(commentService.getComment(comment.getId())).thenReturn(comment);

        mockMvc.perform(get("/api/v1/comments/{id}", comment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(comment.getId().intValue())))
                .andExpect(jsonPath("$.serviceTicketId", is(comment.getServiceTicket().getId().intValue())))
                .andExpect(jsonPath("$.mechanicId", is(comment.getMechanic().getId().intValue())))
                .andExpect(jsonPath("$.dateCreated.[0]", is(comment.getDateCreated().getYear())))
                .andExpect(jsonPath("$.dateCreated.[1]", is(comment.getDateCreated().getMonthValue())))
                .andExpect(jsonPath("$.dateCreated.[2]", is(comment.getDateCreated().getDayOfMonth())))
                .andExpect(jsonPath("$.content", is(comment.getContent())));
    }

    @Test
    void shouldFailToGetCommentById_NotFound() throws Exception {
        when(commentService.getComment(comment.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/comments/{id}", comment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldDeleteComment() throws Exception {
        when(commentService.deleteComment(comment.getId())).thenReturn(comment);

        mockMvc.perform(delete("/api/v1/comments/{id}", comment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToDeleteComment_NotFound() throws Exception {
        when(commentService.deleteComment(comment.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(delete("/api/v1/comments/{id}", comment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldUpdateComment() throws Exception {
        when(commentService.editComment(comment.getId(), editedComment)).thenReturn(editedComment);

        mockMvc.perform(put("/api/v1/comments/{id}", comment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedComment)))
                .andExpect(status().is(200));
    }

    @Test
    @Disabled
    void shouldFailToUpdateComment_NotFound() throws Exception {
        when(commentService.editComment(comment.getId(), editedComment)).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/api/v1/comments/{id}", comment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedComment)))
                .andExpect(status().is(404));
    }
}
