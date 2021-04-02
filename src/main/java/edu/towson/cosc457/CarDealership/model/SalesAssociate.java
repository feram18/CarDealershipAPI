package edu.towson.cosc457.CarDealership.model;

import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.misc.Role;
import edu.towson.cosc457.CarDealership.model.dto.SalesAssociateDto;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "SALES_ASSOCIATE")
@DiscriminatorValue("SALES_ASSOCIATE")
public class SalesAssociate extends Employee {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "manager_id")
    private Manager manager;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "dept_id")
    private Department department;
    @OneToMany(mappedBy = "salesAssociate")
    private List<Client> clients = new ArrayList<>();

    public SalesAssociate() { }

    public SalesAssociate(Integer ssn,
                          String firstName,
                          Character middleInitial,
                          String lastName,
                          Gender gender,
                          LocalDate dateOfBirth,
                          String phoneNumber,
                          String email,
                          Location workLocation,
                          Double salary,
                          LocalDate dateStarted,
                          String address,
                          Double hoursWorked,
                          Role role,
                          String username,
                          String password,
                          Manager manager,
                          Department department,
                          List<Client> clients) {
        super(ssn,
                firstName,
                middleInitial,
                lastName,
                gender,
                dateOfBirth,
                phoneNumber,
                email,
                workLocation,
                salary,
                dateStarted,
                address,
                hoursWorked,
                role,
                username,
                password);
        this.manager = manager;
        this.department = department;
        this.clients = clients;
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }

    public static SalesAssociate from (SalesAssociateDto salesAssociateDto) {
        SalesAssociate salesAssociate = new SalesAssociate();
        salesAssociate.setManager(salesAssociateDto.getManager());
        salesAssociate.setDepartment(salesAssociateDto.getDepartment());
        salesAssociate.setClients(salesAssociateDto.getClientsDto()
                .stream().map(Client::from).collect(Collectors.toList()));
        return salesAssociate;
    }
}
