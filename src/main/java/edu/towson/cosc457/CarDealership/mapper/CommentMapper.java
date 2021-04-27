package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.model.Comment;
import edu.towson.cosc457.CarDealership.model.dto.CommentDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "serviceTicket.id", target = "serviceTicketId")
    @Mapping(source = "mechanic.id", target = "mechanicId")
    CommentDto toDto(Comment comment);

    @InheritInverseConfiguration
    @Mapping(target = "serviceTicket", ignore = true)
    @Mapping(target = "mechanic", ignore = true)
    Comment fromDto(CommentDto commentDto);
}
