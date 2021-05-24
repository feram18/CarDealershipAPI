package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Comment;
import edu.towson.cosc457.CarDealership.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);

    /**
     * Create a new Comment in the database
     * @param comment Comment object to be added to database
     * @return Comment saved on repository
     */
    public Comment addComment(Comment comment) {
        LOGGER.info("Create new Comment in the database");
        return commentRepository.save(comment);
    }

    /**
     * Get All Comments
     * @return List of Comments
     */
    public List<Comment> getComments() {
        LOGGER.info("Get all Comments");
        return StreamSupport
                .stream(commentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
    /**
     * Get Comment by Id
     * @param id identifier of Comment to be fetched
     * @return fetched Comment
     * @throws NotFoundException if no Comment with matching id found
     */
    public Comment getComment(Long id) {
        LOGGER.info("Get Comment with id {}", id);
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.COMMENT.toString(), id, HttpStatus.NOT_FOUND));
    }

    /**
     * Delete Comment by Id
     * @param id identifier of Comment to be deleted
     * @return deleted Comment
     * @throws NotFoundException if no Comment with matching id found
     */
    public Comment deleteComment(Long id) {
        LOGGER.info("Delete Comment with id {}", id);
        Comment comment = getComment(id);
        commentRepository.delete(comment);
        return comment;
    }

    /**
     * Update Comment
     * @param id identifier of Comment to be updated
     * @param comment Comment object with updated fields
     * @return updated Comment
     * @throws NotFoundException if no Comment with matching id found
     */
    @Transactional
    public Comment editComment(Long id, Comment comment) {
        LOGGER.info("Update Comment with id {}", id);
        Comment commentToEdit = getComment(id);
        commentToEdit.setServiceTicket(comment.getServiceTicket());
        commentToEdit.setMechanic(comment.getMechanic());
        commentToEdit.setDateCreated(comment.getDateCreated());
        commentToEdit.setContent(comment.getContent());
        return commentToEdit;
    }
}
