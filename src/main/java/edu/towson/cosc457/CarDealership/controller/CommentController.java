package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.CommentMapper;
import edu.towson.cosc457.CarDealership.model.Comment;
import edu.towson.cosc457.CarDealership.model.dto.CommentDto;
import edu.towson.cosc457.CarDealership.service.CommentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping
    public ResponseEntity<CommentDto> addComment(@RequestBody final CommentDto commentDto) {
        Comment comment = commentService.addComment(commentMapper.fromDto(commentDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentMapper.toDto(comment));
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments() {
        List<Comment> comments = commentService.getComments();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments.stream().map(commentMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable final Long id) {
        Comment comment = commentService.getComment(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentMapper.toDto(comment));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable final Long id) {
        Comment comment = commentService.deleteComment(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentMapper.toDto(comment));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<CommentDto> editComment(@PathVariable final Long id,
                                                  @RequestBody final CommentDto commentDto) {
        Comment comment = commentService.editComment(id, commentMapper.fromDto(commentDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentMapper.toDto(comment));
    }
}
