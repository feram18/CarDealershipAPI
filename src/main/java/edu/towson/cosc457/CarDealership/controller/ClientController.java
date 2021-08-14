package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.ClientMapper;
import edu.towson.cosc457.CarDealership.model.Client;
import edu.towson.cosc457.CarDealership.model.dto.ClientDto;
import edu.towson.cosc457.CarDealership.service.ClientService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    @ApiOperation(value = "Add Client", response = ClientDto.class)
    @PostMapping
    public ResponseEntity<ClientDto> addClient(@RequestBody final ClientDto clientDto) {
        LOGGER.info("POST /api/v1/clients/");
        Client client = clientService.addClient(clientMapper.fromDto(clientDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clientMapper.toDto(client));
    }

    @ApiOperation(value = "Fetch All Clients", response = Iterable.class)
    @GetMapping
    public ResponseEntity<List<ClientDto>> getClients() {
        LOGGER.info("GET /api/v1/clients/");
        List<Client> clients = clientService.getClients();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clients.stream().map(clientMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Fetch Client by Id", response = ClientDto.class)
    @GetMapping(value = "{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/clients/{}", id);
        Client client = clientService.getClient(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientMapper.toDto(client));
    }

    @ApiOperation(value = "Delete Client", response = ClientDto.class)
    @DeleteMapping(value = "{id}")
    public ResponseEntity<ClientDto> deleteClient(@PathVariable final Long id) {
        LOGGER.info("DELETE /api/v1/clients/{}", id);
        Client client = clientService.deleteClient(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientMapper.toDto(client));
    }

    @ApiOperation(value = "Update Client", response = ClientDto.class)
    @PutMapping(value = "{id}")
    public ResponseEntity<ClientDto> editClient(@PathVariable final Long id,
                                                @RequestBody final ClientDto clientDto) {
        LOGGER.info("PUT /api/v1/clients/{}", id);
        Client client = clientService.editClient(id, clientMapper.fromDto(clientDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientMapper.toDto(client));
    }
}
