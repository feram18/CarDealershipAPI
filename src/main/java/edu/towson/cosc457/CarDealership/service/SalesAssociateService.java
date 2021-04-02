package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Client;
import edu.towson.cosc457.CarDealership.model.Employee;
import edu.towson.cosc457.CarDealership.model.SalesAssociate;
import edu.towson.cosc457.CarDealership.repository.SalesAssociateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SalesAssociateService implements EmployeeService<SalesAssociate> {
    private final SalesAssociateRepository salesAssociateRepository;
    private final ClientService clientService;

    @Autowired
    public SalesAssociateService(SalesAssociateRepository salesAssociateRepository, ClientService clientService) {
        this.salesAssociateRepository = salesAssociateRepository;
        this.clientService = clientService;
    }

    @Override
    public SalesAssociate addEmployee(SalesAssociate salesAssociate) {
        return salesAssociateRepository.save(salesAssociate);
    }

    public List<Employee> getEmployees() {
        return StreamSupport
                .stream(salesAssociateRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public SalesAssociate getEmployee(Long id) {
        return (SalesAssociate) salesAssociateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.SALES_ASSOCIATE.toString(), id));
    }

    @Override
    public SalesAssociate deleteEmployee(Long id) {
        SalesAssociate salesAssociate = getEmployee(id);
        salesAssociateRepository.delete(salesAssociate);
        return salesAssociate;
    }

    @Override
    @Transactional
    public SalesAssociate editEmployee(Long id, SalesAssociate salesAssociate) {
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
        salesAssociateToEdit.setRole(salesAssociate.getRole());
        salesAssociateToEdit.setUsername(salesAssociate.getUsername());
        salesAssociateToEdit.setPassword(salesAssociate.getPassword());
        salesAssociateToEdit.setManager(salesAssociate.getManager());
        salesAssociateToEdit.setDepartment(salesAssociate.getDepartment());
        salesAssociateToEdit.setClients(salesAssociate.getClients());
        return salesAssociateToEdit;
    }

    @Transactional
    public SalesAssociate assignClient(Long associateId, Long clientId) {
        SalesAssociate associate = getEmployee(associateId);
        Client client = clientService.getClient(clientId);
        if (Objects.nonNull(client.getSalesAssociate())) {
            throw new AlreadyAssignedException(
                    Entity.CLIENT.toString(),
                    clientId,
                    Entity.SALES_ASSOCIATE.toString(),
                    client.getSalesAssociate().getId()
            );
        }
        associate.addClient(client);
        client.setSalesAssociate(associate);
        return associate;
    }

    @Transactional
    public SalesAssociate removeClient(Long associateId, Long clientId) {
        SalesAssociate associate = getEmployee(associateId);
        Client client = clientService.getClient(clientId);
        associate.removeClient(client);
        return associate;
    }
}
