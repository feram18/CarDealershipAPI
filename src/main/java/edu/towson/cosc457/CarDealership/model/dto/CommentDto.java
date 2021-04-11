package edu.towson.cosc457.CarDealership.model.dto;

import edu.towson.cosc457.CarDealership.model.Comment;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentDto {
    private Long id;
    private ServiceTicket serviceTicket;
    private Mechanic mechanic;
    private LocalDate dateCreated;
    private String content;

    public CommentDto() { }

    public static CommentDto from (Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setServiceTicket(comment.getServiceTicket());
        commentDto.setMechanic(comment.getMechanic());
        commentDto.setDateCreated(comment.getDateCreated());
        commentDto.setContent(comment.getContent());
        return commentDto;
    }
}
