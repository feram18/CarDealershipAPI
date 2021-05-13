package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Comment;
import edu.towson.cosc457.CarDealership.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getComments() {
        return StreamSupport
                .stream(commentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Comment getComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.COMMENT.toString(), id, HttpStatus.NOT_FOUND));
    }

    public Comment deleteComment(Long id) {
        Comment comment = getComment(id);
        commentRepository.delete(comment);
        return comment;
    }

    @Transactional
    public Comment editComment(Long commentId, Comment comment) {
        Comment commentToEdit = getComment(commentId);
        commentToEdit.setId(comment.getId());
        commentToEdit.setServiceTicket(comment.getServiceTicket());
        commentToEdit.setMechanic(comment.getMechanic());
        commentToEdit.setDateCreated(comment.getDateCreated());
        commentToEdit.setContent(comment.getContent());
        return commentToEdit;
    }
}
