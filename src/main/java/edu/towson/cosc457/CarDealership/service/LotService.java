package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Lot;
import edu.towson.cosc457.CarDealership.model.Vehicle;
import edu.towson.cosc457.CarDealership.repository.LotRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class LotService {
    private final LotRepository lotRepository;
    private final VehicleService vehicleService;
    private static final Logger LOGGER = LoggerFactory.getLogger(LotService.class);

    public Lot addLot(Lot lot){
        LOGGER.info("Create new Lot in the database");
        return lotRepository.save(lot);
    }

    public List<Lot> getLots() {
        LOGGER.info("Get all Lots");
        return StreamSupport
                .stream(lotRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Lot getLot(Long id) {
        LOGGER.info("Get Lot with id {}", id);
        return lotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.LOT.toString(), id, HttpStatus.NOT_FOUND));
    }

    public Lot deleteLot(Long id) {
        LOGGER.info("Delete Lot with id {}", id);
        Lot lot = getLot(id);
        lotRepository.delete(lot);
        return lot;
    }

    @Transactional
    public Lot editLot(Long id, Lot lot) {
        LOGGER.info("Update Lot with id {}", id);
        Lot lotToEdit = getLot(id);
        lotToEdit.setSize(lot.getSize());
        lotToEdit.setLocation(lot.getLocation());
        lotToEdit.setVehicles(lot.getVehicles());
        return lotToEdit;
    }

    @Transactional
    public Lot addVehicleToLot(Long lotId, Long vehicleId) {
        LOGGER.info("Add Vehicle with id {} to Lot with id {}", vehicleId, lotId);
        Lot lot = getLot(lotId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);
        if (Objects.nonNull(vehicle.getLot())) {
            throw new AlreadyAssignedException(
                    Entity.VEHICLE.toString(),
                    vehicleId,
                    Entity.LOT.toString(),
                    vehicle.getLot().getId(),
                    HttpStatus.BAD_REQUEST
            );
        }
        lot.addVehicleToLot(vehicle);
        vehicle.setLot(lot);
        return lot;
    }

    @Transactional
    public Lot removeVehicleFromLot(Long lotId, Long vehicleId) {
        LOGGER.info("Remove Vehicle with id {} from Lot with id {}", vehicleId, lotId);
        Lot lot = getLot(lotId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);
        lot.removeVehicleFromLot(vehicle);
        return lot;
    }
}
