package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Client;
import edu.towson.cosc457.CarDealership.model.SalesAssociate;
import edu.towson.cosc457.CarDealership.repository.SalesAssociateRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class SalesAssociateService implements EmployeeService<SalesAssociate> {
    private final SalesAssociateRepository salesAssociateRepository;
    private final ClientService clientService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SalesAssociateService.class);

    @Override
    public SalesAssociate addEmployee(SalesAssociate salesAssociate) {
        LOGGER.info("Create new Sales Associate in the database");
        return salesAssociateRepository.save(salesAssociate);
    }

    @Override
    public List<SalesAssociate> getEmployees() {
        LOGGER.info("Get all Sales Associates");
        return StreamSupport
                .stream(salesAssociateRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public SalesAssociate getEmployee(Long id) {
        LOGGER.info("Get Sales Associate with id {}", id);
        return salesAssociateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.SALES_ASSOCIATE.toString(), id, HttpStatus.NOT_FOUND));
    }

    @Override
    public SalesAssociate deleteEmployee(Long id) {
        LOGGER.info("Delete Sales Associate with id {}", id);
        SalesAssociate salesAssociate = getEmployee(id);
        salesAssociateRepository.delete(salesAssociate);
        return salesAssociate;
    }

    @Override
    @Transactional
    public SalesAssociate editEmployee(Long id, SalesAssociate salesAssociate) {
        LOGGER.info("Update Sales Associate with id {}", id);
        SalesAssociate salesAssociateToEdit = getEmployee(id);
        salesAssociateToEdit.setSsn(salesAssociate.getSsn());
        salesAssociateToEdit.setFirstName(salesAssociate.getFirstName());
        salesAssociateToEdit.setMiddleInitial(salesAssociate.getMiddleInitial());
        salesAssociateToEdit.setLastName(salesAssociate.getLastName());
        salesAssociateToEdit.setGender(salesAssociate.getGender());
        salesAssociateToEdit.setDateOfBirth(salesAssociate.getDateOfBirth());
        salesAssociateToEdit.setPhoneNumber(salesAssociate.getPhoneNumber());
        salesAssociateToEdit.setEmail(salesAssociate.getEmail());
        salesAssociateToEdit.setWorkLocation(salesAssociate.getWorkLocation());
        salesAssociateToEdit.setSalary(salesAssociate.getSalary());
        salesAssociateToEdit.setDateStarted(salesAssociate.getDateStarted());
        salesAssociateToEdit.setAddress(salesAssociate.getAddress());
        salesAssociateToEdit.setHoursWorked(salesAssociate.getHoursWorked());
        salesAssociateToEdit.setManager(salesAssociate.getManager());
        salesAssociateToEdit.setDepartment(salesAssociate.getDepartment());
        salesAssociateToEdit.setClients(salesAssociate.getClients());
        return salesAssociateToEdit;
    }

    @Transactional
    public SalesAssociate assignClient(Long associateId, Long clientId) {
        LOGGER.info("Assign Client with id {} to Sales Associate with id {}", clientId, associateId);
        SalesAssociate salesAssociate = getEmployee(associateId);
        Client client = clientService.getClient(clientId);
        if (Objects.nonNull(client.getSalesAssociate())) {
            throw new AlreadyAssignedException(
                    Entity.CLIENT.toString(),
                    clientId,
                    Entity.SALES_ASSOCIATE.toString(),
                    client.getSalesAssociate().getId(),
                    HttpStatus.BAD_REQUEST
            );
        }
        salesAssociate.addClient(client);
        client.setSalesAssociate(salesAssociate);
        return salesAssociate;
    }

    @Transactional
    public SalesAssociate removeClient(Long associateId, Long clientId) {
        LOGGER.info("Remove Client with id {} from Sales Associate with id {}", clientId, associateId);
        SalesAssociate salesAssociate = getEmployee(associateId);
        Client client = clientService.getClient(clientId);
        salesAssociate.removeClient(client);
        return salesAssociate;
    }
}
