package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "sales_associate", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
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

    public void addClient(Client client) {
        clients.add(client);
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }
}
