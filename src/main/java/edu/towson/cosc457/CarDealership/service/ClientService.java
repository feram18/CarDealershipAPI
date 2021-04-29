package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Client;
import edu.towson.cosc457.CarDealership.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> getClients() {
        return StreamSupport
                .stream(clientRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Client getClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.CLIENT.toString(), id, HttpStatus.NOT_FOUND));
    }

    public Client deleteClient(Long id) {
        Client client = getClient(id);
        clientRepository.delete(client);
        return client;
    }

    @Transactional
    public Client editClient(Long id, Client client) {
        Client clientToEdit = getClient(id);
        clientToEdit.setId(client.getId());
        clientToEdit.setSsn(client.getSsn());
        clientToEdit.setFirstName(client.getFirstName());
        clientToEdit.setLastName(client.getLastName());
        clientToEdit.setGender(client.getGender());
        clientToEdit.setEmail(client.getEmail());
        clientToEdit.setPhoneNumber(client.getPhoneNumber());
        clientToEdit.setAddress(client.getAddress());
        clientToEdit.setSalesAssociate(client.getSalesAssociate());
        clientToEdit.setMinimumPrice(client.getMinimumPrice());
        clientToEdit.setMaximumPrice(client.getMaximumPrice());
        return clientToEdit;
    }
}
