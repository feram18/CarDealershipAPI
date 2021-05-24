package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.AddressMapper;
import edu.towson.cosc457.CarDealership.model.Address;
import edu.towson.cosc457.CarDealership.model.dto.AddressDto;
import edu.towson.cosc457.CarDealership.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    private final AddressMapper addressMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressController.class);

    @PostMapping
    public ResponseEntity<AddressDto> addAddress(@RequestBody final AddressDto addressDto) {
        LOGGER.info("POST /api/v1/addresses/");
        Address address = addressService.addAddress(addressMapper.fromDto(addressDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(addressMapper.toDto(address));
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAddresses() {
        LOGGER.info("GET /api/v1/addresses/");
        List<Address> addresses = addressService.getAddresses();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addresses.stream().map(addressMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<AddressDto> getAddress(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/addresses/{}", id);
        Address address = addressService.getAddress(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addressMapper.toDto(address));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<AddressDto> deleteAddress(@PathVariable final Long id) {
        LOGGER.info("DELETE /api/v1/addresses/{}", id);
        Address address = addressService.deleteAddress(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addressMapper.toDto(address));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<AddressDto> editAddress(@PathVariable final Long id,
                                                  @RequestBody final AddressDto addressDto) {
        LOGGER.info("PUT /api/v1/addresses/{}", id);
        Address address = addressService.editAddress(id, addressMapper.fromDto(addressDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addressMapper.toDto(address));
    }
}
