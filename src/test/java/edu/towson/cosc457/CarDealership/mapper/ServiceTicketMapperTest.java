package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.misc.Status;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.model.Vehicle;
import edu.towson.cosc457.CarDealership.model.dto.ServiceTicketDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTicketMapperTest {
    private static ServiceTicketMapper serviceTicketMapper;
    private static CommentMapper commentMapper;

    @BeforeAll
    public static void setUp() {
        serviceTicketMapper = new ServiceTicketMapperImpl();
        commentMapper = new CommentMapperImpl();
        ReflectionTestUtils.setField(serviceTicketMapper, "commentMapper", commentMapper);
    }

    @Test
    void shouldMapToDto() {
        ServiceTicket serviceTicket = ServiceTicket.builder()
                .id(1L)
                .vehicle(Vehicle.builder()
                        .id(1L)
                        .build())
                .mechanic(Mechanic.builder()
                        .id(1L)
                        .build())
                .dateCreated(LocalDate.now())
                .dateUpdated(LocalDate.now())
                .status(Status.OPEN)
                .build();

        ServiceTicketDto serviceTicketDto = serviceTicketMapper.toDto(serviceTicket);

        assertAll(() -> {
            assertThat(serviceTicketDto).isInstanceOf(ServiceTicketDto.class);
            assertEquals(serviceTicketDto.getId(), serviceTicket.getId());
            assertEquals(serviceTicketDto.getVehicleId(), serviceTicket.getVehicle().getId());
            assertEquals(serviceTicketDto.getMechanicId(), serviceTicket.getMechanic().getId());
            assertEquals(serviceTicketDto.getDateCreated(), serviceTicket.getDateCreated());
            assertEquals(serviceTicketDto.getDateUpdated(), serviceTicket.getDateUpdated());
            assertEquals(serviceTicketDto.getStatus(), serviceTicket.getStatus());
        });
    }

    @Test
    void shouldMapFromDto() {
        ServiceTicketDto serviceTicketDto = ServiceTicketDto.builder()
                .id(1L)
                .vehicleId(1L)
                .mechanicId(1L)
                .dateCreated(LocalDate.now())
                .dateUpdated(LocalDate.now())
                .status(Status.OPEN)
                .build();

        ServiceTicket serviceTicket = serviceTicketMapper.fromDto(serviceTicketDto);

        assertAll(() -> {
            assertThat(serviceTicketDto).isInstanceOf(ServiceTicketDto.class);
            assertEquals(serviceTicketDto.getId(), serviceTicket.getId());
            assertEquals(serviceTicketDto.getVehicleId(), serviceTicket.getVehicle().getId());
            assertEquals(serviceTicketDto.getMechanicId(), serviceTicket.getMechanic().getId());
            assertEquals(serviceTicketDto.getDateCreated(), serviceTicket.getDateCreated());
            assertEquals(serviceTicketDto.getDateUpdated(), serviceTicket.getDateUpdated());
            assertEquals(serviceTicketDto.getStatus(), serviceTicket.getStatus());
        });
    }

    @Test
    void shouldReturnNullEntity() {
        ServiceTicket serviceTicket = serviceTicketMapper.fromDto(null);

        assertThat(serviceTicket).isEqualTo(null);
    }

    @Test
    void shouldReturnNullDto() {
        ServiceTicketDto serviceTicketDto = serviceTicketMapper.toDto(null);

        assertThat(serviceTicketDto).isEqualTo(null);
    }
}
