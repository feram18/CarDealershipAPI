package edu.towson.cosc457.CarDealership.model.dto;

import edu.towson.cosc457.CarDealership.model.Department;
import edu.towson.cosc457.CarDealership.model.Manager;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class MechanicDto extends EmployeeDto {
    private Manager manager;
    private Department department;
    private List<ServiceTicketDto> ticketsDto = new ArrayList<>();
    private List<CommentDto> commentsDto = new ArrayList<>();

    public MechanicDto() { }

    public static MechanicDto from (Mechanic mechanic) {
        MechanicDto mechanicDto = new MechanicDto();
        mechanicDto.setId(mechanic.getId());
        mechanicDto.setFirstName(mechanic.getFirstName());
        mechanicDto.setMiddleInitial(mechanic.getMiddleInitial());
        mechanicDto.setLastName(mechanic.getLastName());
        mechanicDto.setGender(mechanic.getGender());
        mechanicDto.setDateOfBirth(mechanic.getDateOfBirth());
        mechanicDto.setPhoneNumber(mechanic.getPhoneNumber());
        mechanicDto.setEmail(mechanic.getEmail());
        mechanicDto.setWorkLocation(mechanic.getWorkLocation());
        mechanicDto.setSalary(mechanic.getSalary());
        mechanicDto.setDateStarted(mechanic.getDateStarted());
        mechanicDto.setAddress(mechanic.getAddress());
        mechanicDto.setHoursWorked(mechanic.getHoursWorked());
        mechanicDto.setEmployeeType(mechanic.getEmployeeType());
        mechanicDto.setUsername(mechanic.getUsername());
        mechanicDto.setManager(mechanic.getManager());
        mechanicDto.setDepartment(mechanic.getDepartment());
        mechanicDto.setTicketsDto(mechanic.getTickets()
                .stream().map(ServiceTicketDto::from).collect(Collectors.toList()));
        mechanicDto.setCommentsDto(mechanic.getComments()
                .stream().map(CommentDto::from).collect(Collectors.toList()));
        return mechanicDto;
    }
}
