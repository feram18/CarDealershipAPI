package edu.towson.cosc457.CarDealership.model.dto;

import edu.towson.cosc457.CarDealership.misc.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceTicketDto {
    private Long id;
    private Long vehicleId;
    private Long mechanicId;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private Status status;
    private List<CommentDto> comments = new ArrayList<>();
}
