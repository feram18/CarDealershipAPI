package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.LotMapper;
import edu.towson.cosc457.CarDealership.mapper.VehicleMapper;
import edu.towson.cosc457.CarDealership.model.Lot;
import edu.towson.cosc457.CarDealership.model.dto.LotDto;
import edu.towson.cosc457.CarDealership.model.dto.VehicleDto;
import edu.towson.cosc457.CarDealership.service.LotService;
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
@RequestMapping("/api/v1/lots")
@RequiredArgsConstructor
public class LotController {
    private final LotService lotService;
    private final LotMapper lotMapper;
    private final VehicleMapper vehicleMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(LotController.class);

    @ApiOperation(value = "Add Lot", response = LotDto.class)
    @PostMapping
    public ResponseEntity<LotDto> addLot(@RequestBody final LotDto lotDto) {
        LOGGER.info("POST /api/v1/lots/");
        Lot lot = lotService.addLot(lotMapper.fromDto(lotDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lotMapper.toDto(lot));
    }

    @ApiOperation(value = "Fetch All Lots", response = Iterable.class)
    @GetMapping
    public ResponseEntity<List<LotDto>> getLots() {
        LOGGER.info("GET /api/v1/lots/");
        List<Lot> lots = lotService.getLots();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lots.stream().map(lotMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Fetch Lot by Id", response = LotDto.class)
    @GetMapping(value = "{id}")
    public ResponseEntity<LotDto> getLot(@PathVariable final Long id) {
        LOGGER.info("POST /api/v1/lots/{}", id);
        Lot lot = lotService.getLot(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotMapper.toDto(lot));
    }

    @ApiOperation(value = "Delete Lot", response = LotDto.class)
    @DeleteMapping(value = "{id}")
    public ResponseEntity<LotDto> deleteLot(@PathVariable final Long id) {
        LOGGER.info("DELETE /api/v1/lots/{}", id);
        Lot lot = lotService.deleteLot(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotMapper.toDto(lot));
    }

    @ApiOperation(value = "Update Lot", response = LotDto.class)
    @PutMapping(value = "{id}")
    public ResponseEntity<LotDto> editLot(@PathVariable final Long id,
                                          @RequestBody final LotDto lotDto) {
        LOGGER.info("PUT /api/v1/lots/{}", id);
        Lot lot = lotService.editLot(id, lotMapper.fromDto(lotDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotMapper.toDto(lot));
    }

    @ApiOperation(value = "Fetch All Vehicles in Lot", response = Iterable.class)
    @GetMapping(value = "{id}/vehicles")
    public ResponseEntity<List<VehicleDto>> getVehicles(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/lots/{}/vehicles", id);
        Lot lot = lotService.getLot(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lot.getVehicles().stream().map(vehicleMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Add Vehicle to Lot", response = LotDto.class)
    @PostMapping(value = "{lotId}/vehicles/{vehicleId}/add")
    public ResponseEntity<LotDto> addVehicleToLot(@PathVariable final Long lotId,
                                                  @PathVariable final Long vehicleId) {
        LOGGER.info("POST /api/v1/lots/{}/vehicles/{}/add", lotId, vehicleId);
        Lot lot = lotService.addVehicleToLot(lotId, vehicleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotMapper.toDto(lot));
    }

    @ApiOperation(value = "Remove Vehicle from Lot", response = LotDto.class)
    @DeleteMapping(value = "{lotId}/vehicles/{vehicleId}/remove")
    public ResponseEntity<LotDto> removeVehicleFromLot(@PathVariable final Long lotId,
                                                  @PathVariable final Long vehicleId) {
        LOGGER.info("DELETE /api/v1/lots/{}/vehicles/{}/remove", lotId, vehicleId);
        Lot lot = lotService.removeVehicleFromLot(lotId, vehicleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotMapper.toDto(lot));
    }
}
