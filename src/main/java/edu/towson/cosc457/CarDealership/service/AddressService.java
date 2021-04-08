package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Address;
import edu.towson.cosc457.CarDealership.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> getAddresses() {
        return StreamSupport
                .stream(addressRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Address getAddress(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.ADDRESS.toString(), id));
    }

    public Address deleteAddress(Long id) {
        Address address = getAddress(id);
        addressRepository.delete(address);
        return address;
    }

    @Transactional
    public Address editAddress(Long id, Address address) {
        Address addressToEdit = getAddress(id);
        addressToEdit.setId(address.getId());
        addressToEdit.setStreet(address.getStreet());
        addressToEdit.setCity(address.getCity());
        addressToEdit.setState(address.getState());
        addressToEdit.setZipCode(address.getZipCode());
        return addressToEdit;
    }
}
