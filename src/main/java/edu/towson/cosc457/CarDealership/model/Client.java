package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.dto.ClientDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "client", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", updatable = false)
    private Long id;
    @NotNull
    @Column(name = "client_ssn", unique = true, length = 11)
    private String ssn;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_no", length = 12)
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "sales_associate_id")
    private SalesAssociate salesAssociate;
    @Column(name = "min_price")
    private Double minimumPrice;
    @Column(name = "max_price")
    private Double maximumPrice;

    public static Client from (ClientDto clientDto) {
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setGender(clientDto.getGender());
        client.setEmail(clientDto.getEmail());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        client.setAddress(clientDto.getAddress());
        client.setSalesAssociate(clientDto.getSalesAssociate());
        client.setMinimumPrice(clientDto.getMinimumPrice());
        client.setMaximumPrice(clientDto.getMaximumPrice());
        return client;
    }
}
