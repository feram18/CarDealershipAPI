package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.model.Client;
import edu.towson.cosc457.CarDealership.model.dto.ClientDto;
import edu.towson.cosc457.CarDealership.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientDto> addClient(@RequestBody final ClientDto clientDto) {
        Client location = clientService.addClient(Client.from(clientDto));
        return new ResponseEntity<>(ClientDto.from(location), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getClients() {
        List<Client> locations = clientService.getClients();
        List<ClientDto> locationsDto = locations.stream().map(ClientDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(locationsDto, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable final Long id) {
        Client location = clientService.getClient(id);
        return new ResponseEntity<>(ClientDto.from(location), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ClientDto> deleteClient(@PathVariable final Long id) {
        Client location = clientService.deleteClient(id);
        return new ResponseEntity<>(ClientDto.from(location), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<ClientDto> editClient(@PathVariable final Long id,
                                                @RequestBody final ClientDto clientDto) {
        Client location = clientService.editClient(id, Client.from(clientDto));
        return new ResponseEntity<>(ClientDto.from(location), HttpStatus.OK);
    }
}
