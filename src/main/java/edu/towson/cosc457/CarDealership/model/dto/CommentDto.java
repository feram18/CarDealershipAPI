package edu.towson.cosc457.CarDealership.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private Long serviceTicketId;
    private Long mechanicId;
    private LocalDate dateCreated;
    private String content;
}
