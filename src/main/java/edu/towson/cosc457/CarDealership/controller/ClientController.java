package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.ClientMapper;
import edu.towson.cosc457.CarDealership.model.Client;
import edu.towson.cosc457.CarDealership.model.dto.ClientDto;
import edu.towson.cosc457.CarDealership.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    public ResponseEntity<ClientDto> addClient(@RequestBody final ClientDto clientDto) {
        Client client = clientService.addClient(clientMapper.fromDto(clientDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clientMapper.toDto(client));
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getClients() {
        List<Client> clients = clientService.getClients();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clients.stream().map(clientMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable final Long id) {
        Client client = clientService.getClient(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientMapper.toDto(client));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ClientDto> deleteClient(@PathVariable final Long id) {
        Client client = clientService.deleteClient(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientMapper.toDto(client));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<ClientDto> editClient(@PathVariable final Long id,
                                                @RequestBody final ClientDto clientDto) {
        Client client = clientService.editClient(id, clientMapper.fromDto(clientDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientMapper.toDto(client));
    }
}
