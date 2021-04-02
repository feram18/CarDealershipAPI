package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.model.Comment;
import edu.towson.cosc457.CarDealership.model.dto.CommentDto;
import edu.towson.cosc457.CarDealership.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> addComment(@RequestBody final CommentDto commentDto) {
        Comment comment = commentService.addComment(Comment.from(commentDto));
        return new ResponseEntity<>(CommentDto.from(comment), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments() {
        List<Comment> comments = commentService.getComments();
        List<CommentDto> commentsDto = comments.stream().map(CommentDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(commentsDto, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable final Long id) {
        Comment comment = commentService.getComment(id);
        return new ResponseEntity<>(CommentDto.from(comment), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<CommentDto> deleteComment(@PathVariable final Long id) {
        Comment comment = commentService.deleteComment(id);
        return new ResponseEntity<>(CommentDto.from(comment), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<CommentDto> editComment(@PathVariable final Long id,
                                                  @RequestBody final CommentDto commentDto) {
        Comment editedComment = commentService.editComment(id, Comment.from(commentDto));
        return new ResponseEntity<>(CommentDto.from(editedComment), HttpStatus.OK);
    }
}
