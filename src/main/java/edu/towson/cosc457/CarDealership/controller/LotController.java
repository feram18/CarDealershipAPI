package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.model.Lot;
import edu.towson.cosc457.CarDealership.model.dto.LotDto;
import edu.towson.cosc457.CarDealership.model.dto.VehicleDto;
import edu.towson.cosc457.CarDealership.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lots")
public class LotController {
    private final LotService lotService;

    @Autowired
    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    @PostMapping
    public ResponseEntity<LotDto> addLot(@RequestBody final LotDto lotDto) {
        Lot lot = lotService.addLot(Lot.from(lotDto));
        return new ResponseEntity<>(LotDto.from(lot), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LotDto>> getLots() {
        List<Lot> lots = lotService.getLots();
        List<LotDto> lotsDto = lots.stream().map(LotDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(lotsDto, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<LotDto> getLot(@PathVariable final Long id) {
        Lot lot = lotService.getLot(id);
        return new ResponseEntity<>(LotDto.from(lot), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<LotDto> deleteLot(@PathVariable final Long id) {
        Lot lot = lotService.deleteLot(id);
        return new ResponseEntity<>(LotDto.from(lot), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<LotDto> editLot(@PathVariable final Long id,
                                          @RequestBody final LotDto lotDto) {
        Lot editedLot = lotService.editLot(id, Lot.from(lotDto));
        return new ResponseEntity<>(LotDto.from(editedLot), HttpStatus.OK);
    }

    @GetMapping(value = "{lotId}/vehicles")
    public ResponseEntity<List<VehicleDto>> getVehicles(@PathVariable final Long id) {
        Lot lot = lotService.getLot(id);
        List<VehicleDto> vehiclesDto = lot.getVehicles().stream().map(VehicleDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(vehiclesDto, HttpStatus.OK);
    }

    @PostMapping(value = "{lotId}/vehicles/{vehicleId}/add")
    public ResponseEntity<LotDto> addVehicleToLot(@PathVariable final Long lotId,
                                                  @PathVariable final Long vehicleId) {
        Lot lot = lotService.addVehicleToLot(lotId, vehicleId);
        return new ResponseEntity<>(LotDto.from(lot), HttpStatus.OK);
    }

    @PostMapping(value = "{lotId}/vehicles/{vehicleId}/remove")
    public ResponseEntity<LotDto> removeVehicleFromLot(@PathVariable final Long lotId,
                                                  @PathVariable final Long vehicleId) {
        Lot lot = lotService.removeVehicleFromLot(lotId, vehicleId);
        return new ResponseEntity<>(LotDto.from(lot), HttpStatus.OK);
    }
}
