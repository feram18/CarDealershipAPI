package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.dto.SalesAssociateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "sales_associate", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("SALES_ASSOCIATE")
@EqualsAndHashCode(callSuper = true)
public class SalesAssociate extends Employee {
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "manager_id")
    private Manager manager;
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "dept_id")
    private Department department;
    @JsonManagedReference
    @OneToMany(mappedBy = "salesAssociate")
    private List<Client> clients = new ArrayList<>();

    public SalesAssociate(String ssn,
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
                          Address address,
                          Double hoursWorked,
                          EmployeeType employeeType,
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
                employeeType);
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
