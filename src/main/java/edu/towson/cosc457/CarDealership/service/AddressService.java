package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Address;
import edu.towson.cosc457.CarDealership.repository.AddressRepository;
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
public class AddressService {
    private final AddressRepository addressRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

    /**
     * Create a new Address in the database
     * @param address Address object to be added to database
     * @return Address saved on repository
     */
    public Address addAddress(Address address) {
        LOGGER.info("Create new Address in the database");
        return addressRepository.save(address);
    }

    /**
     * Get All Addresses
     * @return List of Addresses
     */
    public List<Address> getAddresses() {
        LOGGER.info("Get all Addresses");
        return StreamSupport
                .stream(addressRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Get Address by Id
     * @param id identifier of Address to be fetched
     * @return fetched Address
     * @throws NotFoundException if no Address with matching id found
     */
    public Address getAddress(Long id) {
        LOGGER.info("Get Address with id {}", id);
        return addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.ADDRESS.toString(), id, HttpStatus.NOT_FOUND));
    }

    /**
     * Delete Address by Id
     * @param id identifier of Address to be deleted
     * @return deleted Address
     * @throws NotFoundException if no Address with matching id found
     */
    public Address deleteAddress(Long id) {
        LOGGER.info("Delete Address with id {}", id);
        Address address = getAddress(id);
        addressRepository.delete(address);
        return address;
    }

    /**
     * Update Address
     * @param id identifier of Address to be updated
     * @param address Address object with updated fields
     * @return updated Address
     * @throws NotFoundException if no Address with matching id found
     */
    @Transactional
    public Address editAddress(Long id, Address address) {
        LOGGER.info("Update Address with id {}", id);
        Address addressToEdit = getAddress(id);
        addressToEdit.setStreet(address.getStreet());
        addressToEdit.setCity(address.getCity());
        addressToEdit.setState(address.getState());
        addressToEdit.setZipCode(address.getZipCode());
        return addressToEdit;
    }
}
