package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.LotMapper;
import edu.towson.cosc457.CarDealership.mapper.VehicleMapper;
import edu.towson.cosc457.CarDealership.model.Lot;
import edu.towson.cosc457.CarDealership.model.dto.LotDto;
import edu.towson.cosc457.CarDealership.model.dto.VehicleDto;
import edu.towson.cosc457.CarDealership.service.LotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lots")
@RequiredArgsConstructor
public class LotController {
    private final LotService lotService;
    private final LotMapper lotMapper;
    private final VehicleMapper vehicleMapper;

    @PostMapping
    public ResponseEntity<LotDto> addLot(@RequestBody final LotDto lotDto) {
        Lot lot = lotService.addLot(lotMapper.fromDto(lotDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lotMapper.toDto(lot));
    }

    @GetMapping
    public ResponseEntity<List<LotDto>> getLots() {
        List<Lot> lots = lotService.getLots();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lots.stream().map(lotMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<LotDto> getLot(@PathVariable final Long id) {
        Lot lot = lotService.getLot(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotMapper.toDto(lot));
    }

    @DeleteMapping
    public ResponseEntity<LotDto> deleteLot(@PathVariable final Long id) {
        Lot lot = lotService.deleteLot(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotMapper.toDto(lot));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<LotDto> editLot(@PathVariable final Long id,
                                          @RequestBody final LotDto lotDto) {
        Lot lot = lotService.editLot(id, lotMapper.fromDto(lotDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotMapper.toDto(lot));
    }

    @GetMapping(value = "{id}/vehicles")
    public ResponseEntity<List<VehicleDto>> getVehicles(@PathVariable final Long id) {
        Lot lot = lotService.getLot(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lot.getVehicles().stream().map(vehicleMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping(value = "{lotId}/vehicles/{vehicleId}/add")
    public ResponseEntity<LotDto> addVehicleToLot(@PathVariable final Long lotId,
                                                  @PathVariable final Long vehicleId) {
        Lot lot = lotService.addVehicleToLot(lotId, vehicleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotMapper.toDto(lot));
    }

    @PostMapping(value = "{lotId}/vehicles/{vehicleId}/remove")
    public ResponseEntity<LotDto> removeVehicleFromLot(@PathVariable final Long lotId,
                                                  @PathVariable final Long vehicleId) {
        Lot lot = lotService.removeVehicleFromLot(lotId, vehicleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotMapper.toDto(lot));
    }
}
