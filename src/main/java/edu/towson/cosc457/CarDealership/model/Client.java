package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.dto.ClientDto;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CLIENT")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id",
            updatable = false)
    private Long id;
    @NotNull
    @Column(name = "client_ssn",
            unique = true,
            length = 11)
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
    @Column(name = "phone_no",
            length = 12)
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

    public Client() { }

    public Client(String ssn,
                  String firstName,
                  String lastName,
                  Gender gender,
                  String email,
                  String phoneNumber,
                  Address address,
                  SalesAssociate salesAssociate,
                  Double minimumPrice,
                  Double maximumPrice) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.salesAssociate = salesAssociate;
        this.minimumPrice = minimumPrice;
        this.maximumPrice = maximumPrice;
    }

    public static Client from (ClientDto clientDto) {
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setSsn(clientDto.getSsn());
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
