package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.CommentMapper;
import edu.towson.cosc457.CarDealership.model.Comment;
import edu.towson.cosc457.CarDealership.model.dto.CommentDto;
import edu.towson.cosc457.CarDealership.service.CommentService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @ApiOperation(value = "Add Comment", response = CommentDto.class)
    @PostMapping
    public ResponseEntity<CommentDto> addComment(@RequestBody final CommentDto commentDto) {
        LOGGER.info("POST /api/v1/comments/");
        Comment comment = commentService.addComment(commentMapper.fromDto(commentDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentMapper.toDto(comment));
    }

    @ApiOperation(value = "Fetch All Comments", response = Iterable.class)
    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments() {
        LOGGER.info("GET /api/v1/comments/");
        List<Comment> comments = commentService.getComments();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments.stream().map(commentMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Fetch Comment by Id", response = CommentDto.class)
    @GetMapping(value = "{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/comments/{}", id);
        Comment comment = commentService.getComment(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentMapper.toDto(comment));
    }

    @ApiOperation(value = "Delete Comment", response = CommentDto.class)
    @DeleteMapping(value = "{id}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable final Long id) {
        LOGGER.info("DELETE /api/v1/comments/{}", id);
        Comment comment = commentService.deleteComment(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentMapper.toDto(comment));
    }

    @ApiOperation(value = "Update Comment", response = CommentDto.class)
    @PutMapping(value = "{id}")
    public ResponseEntity<CommentDto> editComment(@PathVariable final Long id,
                                                  @RequestBody final CommentDto commentDto) {
        LOGGER.info("PUT /api/v1/comments/{}", id);
        Comment comment = commentService.editComment(id, commentMapper.fromDto(commentDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentMapper.toDto(comment));
    }
}
