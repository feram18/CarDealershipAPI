package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Client;
import edu.towson.cosc457.CarDealership.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    /**
     * Create a new Client in the database
     * @param client Client object to be added to database
     * @return Client saved on repository
     */
    public Client addClient(Client client) {
        LOGGER.info("Create new Client in the database");
        return clientRepository.save(client);
    }

    /**
     * Get All Clients
     * @return List of Clients
     */
    public List<Client> getClients() {
        LOGGER.info("Get all Clients");
        return StreamSupport
                .stream(clientRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Get Client by Id
     * @param id identifier of Client to be fetched
     * @return fetched Client
     * @throws NotFoundException if no Client with matching id found
     */
    public Client getClient(Long id) {
        LOGGER.info("Get Client with id {}", id);
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.CLIENT.toString(), id, HttpStatus.NOT_FOUND));
    }
    /**
     * Delete Client by Id
     * @param id identifier of Client to be deleted
     * @return deleted Client
     * @throws NotFoundException if no Client with matching id found
     */
    public Client deleteClient(Long id) {
        LOGGER.info("Delete Client with id {}", id);
        Client client = getClient(id);
        clientRepository.delete(client);
        return client;
    }

    /**
     * Update Client
     * @param id identifier of Client to be updated
     * @param client Client object with updated fields
     * @return updated Client
     * @throws NotFoundException if no Client with matching id found
     */
    @Transactional
    public Client editClient(Long id, Client client) {
        LOGGER.info("Update Client with id {}", id);
        Client clientToEdit = getClient(id);
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
