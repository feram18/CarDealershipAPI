package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Lot;
import edu.towson.cosc457.CarDealership.model.Vehicle;
import edu.towson.cosc457.CarDealership.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LotService {
    private final LotRepository lotRepository;
    private final VehicleService vehicleService;

    @Autowired
    public LotService(LotRepository lotRepository, VehicleService vehicleService) {
        this.lotRepository = lotRepository;
        this.vehicleService = vehicleService;
    }

    public Lot addLot(Lot lot){
        return lotRepository.save(lot);
    }

    public List<Lot> getLots() {
        return StreamSupport
                .stream(lotRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Lot getLot(Long id) {
        return lotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.LOT.toString(), id));
    }

    public Lot deleteLot(Long id) {
        Lot lot = getLot(id);
        lotRepository.delete(lot);
        return lot;
    }

    @Transactional
    public Lot editLot(Long id, Lot lot) {
        Lot lotToEdit = getLot(id);
        lotToEdit.setId(lot.getId());
        lotToEdit.setSize(lot.getSize());
        lotToEdit.setLocation(lot.getLocation());
        return lotToEdit;
    }

    @Transactional
    public Lot addVehicleToLot(Long lotId, Long vehicleId) {
        Lot lot = getLot(lotId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);
        if (Objects.nonNull(vehicle.getLot())) {
            throw new AlreadyAssignedException(
                    Entity.VEHICLE.toString(),
                    vehicleId,
                    Entity.LOT.toString(),
                    vehicle.getLot().getId()
            );
        }
        lot.addVehicleToLot(vehicle);
        vehicle.setLot(lot);
        return lot;
    }

    @Transactional
    public Lot removeVehicleFromLot(Long lotId, Long vehicleId) {
        Lot lot = getLot(lotId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);
        lot.removeVehicleFromLot(vehicle);
        return lot;
    }
}
