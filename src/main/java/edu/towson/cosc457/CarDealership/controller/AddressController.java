package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.model.Address;
import edu.towson.cosc457.CarDealership.model.dto.AddressDto;
import edu.towson.cosc457.CarDealership.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/addresses")
@AllArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDto> addAddress(@RequestBody final AddressDto addressDto) {
        Address address = addressService.addAddress(Address.from(addressDto));
        return new ResponseEntity<>(AddressDto.from(address), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAddresses() {
        List<Address> addresses = addressService.getAddresses();
        List<AddressDto> addressesDto = addresses.stream().map(AddressDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(addressesDto, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<AddressDto> getAddress(@PathVariable final Long id) {
        Address address = addressService.getAddress(id);
        return new ResponseEntity<>(AddressDto.from(address), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<AddressDto> deleteAddress(@PathVariable final Long id) {
        Address address = addressService.deleteAddress(id);
        return new ResponseEntity<>(AddressDto.from(address), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<AddressDto> editAddress(@PathVariable final Long id,
                                                  @RequestBody final AddressDto addressDto) {
        Address address = addressService.editAddress(id, Address.from(addressDto));
        return new ResponseEntity<>(AddressDto.from(address), HttpStatus.OK);
    }
}
