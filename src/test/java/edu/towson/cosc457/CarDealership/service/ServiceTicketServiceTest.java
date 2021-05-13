package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Status;
import edu.towson.cosc457.CarDealership.model.*;
import edu.towson.cosc457.CarDealership.repository.ServiceTicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTicketServiceTest {
    @InjectMocks
    private ServiceTicketService serviceTicketService;
    @Mock
    private ServiceTicketRepository serviceTicketRepository;
    @Mock
    private CommentService commentService;
    @Captor
    private ArgumentCaptor<ServiceTicket> serviceTicketArgumentCaptor;
    private ServiceTicket serviceTicket;
    private ServiceTicket editedServiceTicket;
    private Comment comment;

    @BeforeEach
    public void setUp() {
        serviceTicket = ServiceTicket.builder()
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
                .comments(new ArrayList<>())
                .build();

        editedServiceTicket = ServiceTicket.builder()
                .id(1L)
                .vehicle(Vehicle.builder()
                        .id(1L)
                        .build())
                .mechanic(Mechanic.builder()
                        .id(1L)
                        .build())
                .dateCreated(LocalDate.now())
                .dateUpdated(LocalDate.now())
                .status(Status.RESOLVED)
                .comments(new ArrayList<>())
                .build();

        comment = Comment.builder()
                .id(1L)
                .mechanic(Mechanic.builder()
                        .id(1L)
                        .build())
                .dateCreated(LocalDate.now())
                .content("Content")
                .build();
    }

    @Test
    void shouldSaveServiceTicket() {
        serviceTicketService.addServiceTicket(serviceTicket);

        verify(serviceTicketRepository, times(1)).save(serviceTicketArgumentCaptor.capture());

        assertThat(serviceTicketArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(serviceTicket);
    }

    @Test
    void shouldFailToSaveNullServiceTicket() {
        serviceTicketService.addServiceTicket(null);

        verify(serviceTicketRepository, never()).save(any(ServiceTicket.class));
    }

    @Test
    void shouldGetServiceTicketById() {
        Mockito.when(serviceTicketRepository.findById(serviceTicket.getId())).thenReturn(Optional.of(serviceTicket));

        ServiceTicket actualServiceTicket = serviceTicketService.getServiceTicket(serviceTicket.getId());
        verify(serviceTicketRepository, times(1)).findById(serviceTicket.getId());

        assertAll(() -> {
            assertThat(actualServiceTicket).isNotNull();
            assertThat(actualServiceTicket).usingRecursiveComparison().isEqualTo(serviceTicket);
        });
    }

    @Test
    void shouldFailToGetServiceTicketById() {
        Mockito.when(serviceTicketRepository.findById(serviceTicket.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> serviceTicketService.getServiceTicket(serviceTicket.getId()));

        verify(serviceTicketRepository, times(1)).findById(serviceTicket.getId());
    }

    @Test
    void shouldGetAllServiceTickets() {
        List<ServiceTicket> expectedServiceTickets = new ArrayList<>();
        expectedServiceTickets.add(serviceTicket);
        expectedServiceTickets.add(ServiceTicket.builder()
                .id(2L)
                .build());
        expectedServiceTickets.add(ServiceTicket.builder()
                .id(3L)
                .build());

        Mockito.when(serviceTicketRepository.findAll()).thenReturn(expectedServiceTickets);
        List<ServiceTicket> actualServiceTickets = serviceTicketService.getServiceTickets();
        verify(serviceTicketRepository, times(1)).findAll();

        assertAll(() -> {
            assertThat(actualServiceTickets).isNotNull();
            assertThat(actualServiceTickets.size()).isEqualTo(expectedServiceTickets.size());
        });
    }

    @Test
    void shouldGetAllServiceTickets_EmptyList() {
        Mockito.when(serviceTicketRepository.findAll()).thenReturn(new ArrayList<>());

        List<ServiceTicket> actualServiceTickets = serviceTicketService.getServiceTickets();

        assertThat(actualServiceTickets).isEmpty();
    }

    @Test
    void shouldDeleteServiceTicket() {
        Mockito.when(serviceTicketRepository.findById(serviceTicket.getId())).thenReturn(Optional.of(serviceTicket));

        ServiceTicket deletedServiceTicket = serviceTicketService.deleteServiceTicket(serviceTicket.getId());

        verify(serviceTicketRepository, times(1)).delete(serviceTicket);

        assertAll(() -> {
            assertThat(deletedServiceTicket).isNotNull();
            assertThat(deletedServiceTicket).usingRecursiveComparison().isEqualTo(serviceTicket);
        });
    }

    @Test
    void shouldFailToDeleteServiceTicket() {
        Mockito.when(serviceTicketRepository.findById(serviceTicket.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> serviceTicketService.deleteServiceTicket(serviceTicket.getId()));

        verify(serviceTicketRepository, never()).delete(any(ServiceTicket.class));
    }

    @Test
    void shouldUpdateServiceTicket() {
        Mockito.when(serviceTicketRepository.findById(serviceTicket.getId())).thenReturn(Optional.of(serviceTicket));

        ServiceTicket updatedServiceTicket =
                serviceTicketService.editServiceTicket(serviceTicket.getId(), editedServiceTicket);

        assertAll(() -> {
            assertThat(updatedServiceTicket).isNotNull();
            assertThat(updatedServiceTicket).usingRecursiveComparison().isEqualTo(editedServiceTicket);
        });
    }

    @Test
    void shouldFailToUpdateServiceTicket() {
        Mockito.when(serviceTicketRepository.findById(serviceTicket.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> serviceTicketService.editServiceTicket(serviceTicket.getId(), editedServiceTicket));
    }

    @Test
    void shouldAddCommentToServiceTicket() {
        Mockito.when(serviceTicketRepository.findById(serviceTicket.getId())).thenReturn(Optional.of(serviceTicket));
        Mockito.when(commentService.getComment(comment.getId())).thenReturn(comment);

        ServiceTicket updatedServiceTicket =
                serviceTicketService.addCommentToTicket(serviceTicket.getId(), comment.getId());

        assertAll(() -> {
            assertThat(updatedServiceTicket.getComments().size()).isEqualTo(1);
            assertThat(updatedServiceTicket.getComments().get(0)).usingRecursiveComparison().isEqualTo(comment);
        });
    }

    @Test
    void shouldFailToAddCommentToServiceTicket() {
        Mockito.when(serviceTicketRepository.findById(serviceTicket.getId())).thenReturn(Optional.of(serviceTicket));
        Mockito.when(commentService.getComment(comment.getId())).thenReturn(comment);

        serviceTicketService.addCommentToTicket(serviceTicket.getId(), comment.getId()); // Assign once

        // Attempt to assign again
        assertThrows(AlreadyAssignedException.class,
                () -> serviceTicketService.addCommentToTicket(serviceTicket.getId(), comment.getId()));
    }

    @Test
    void shouldRemoveCommentFromServiceTicket() {
        Mockito.when(serviceTicketRepository.findById(serviceTicket.getId())).thenReturn(Optional.of(serviceTicket));
        Mockito.when(commentService.getComment(comment.getId())).thenReturn(comment);

        serviceTicketService.addCommentToTicket(serviceTicket.getId(), comment.getId());
        ServiceTicket updatedServiceTicket =
                serviceTicketService.removeCommentFromTicket(serviceTicket.getId(), comment.getId());

        assertThat(updatedServiceTicket.getComments().size()).isEqualTo(0);
    }
}
