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

    /**
     * Create a new Lot in the database
     * @param lot Lot object to be added to database
     * @return Lot saved on repository
     */
    public Lot addLot(Lot lot){
        LOGGER.info("Create new Lot in the database");
        return lotRepository.save(lot);
    }

    /**
     * Get All Lots
     * @return List of Lots
     */
    public List<Lot> getLots() {
        LOGGER.info("Get all Lots");
        return StreamSupport
                .stream(lotRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Get Lot by Id
     * @param id identifier of Lot to be fetched
     * @return fetched Lot
     * @throws NotFoundException if no Lot with matching id found
     */
    public Lot getLot(Long id) {
        LOGGER.info("Get Lot with id {}", id);
        return lotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.LOT.toString(), id, HttpStatus.NOT_FOUND));
    }

    /**
     * Delete Lot by Id
     * @param id identifier of Lot to be deleted
     * @return deleted Lot
     * @throws NotFoundException if no Lot with matching id found
     */
    public Lot deleteLot(Long id) {
        LOGGER.info("Delete Lot with id {}", id);
        Lot lot = getLot(id);
        lotRepository.delete(lot);
        return lot;
    }

    /**
     * Update Lot
     * @param id identifier of Lot to be updated
     * @param lot Lot object with updated fields
     * @return updated Lot
     * @throws NotFoundException if no Lot with matching id found
     */
    @Transactional
    public Lot editLot(Long id, Lot lot) {
        LOGGER.info("Update Lot with id {}", id);
        Lot lotToEdit = getLot(id);
        lotToEdit.setSize(lot.getSize());
        lotToEdit.setLocation(lot.getLocation());
        lotToEdit.setVehicles(lot.getVehicles());
        return lotToEdit;
    }

    /**
     * Add Vehicle to Lot
     * @param lotId identifier of Lot to be updated
     * @param vehicleId identifier of Vehicle to be added to Lot
     * @return updated Lot entity
     * @throws NotFoundException if no Lot or Vehicle with matching lotId/vehicleId were found
     * @throws AlreadyAssignedException if Vehicle already assigned to a Lot
     */
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

    /**
     * Remove Vehicle from assigned Lot
     * @param lotId identifier of Lot be updated
     * @param vehicleId identifier of the Vehicle to be removed to Lot
     * @return updated Lot entity
     * @throws NotFoundException if no Lot or Vehicle with matching lotId/vehicleId were found
     */
    @Transactional
    public Lot removeVehicleFromLot(Long lotId, Long vehicleId) {
        LOGGER.info("Remove Vehicle with id {} from Lot with id {}", vehicleId, lotId);
        Lot lot = getLot(lotId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);
        lot.removeVehicleFromLot(vehicle);
        return lot;
    }
}
