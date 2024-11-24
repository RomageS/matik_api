package com.integradora.matik.controller;

import com.integradora.matik.dto.vehicleDto;
import com.integradora.matik.repository.vehicleRepo;
import com.integradora.matik.service.vehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin

public class vehicleController {

    private final vehicleService VehicleService;


    @Autowired
    public vehicleController(vehicleService VehicleService, vehicleRepo vehicleRepo) {
        this.VehicleService = VehicleService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody vehicleDto VehicleDto) {
        return new ResponseEntity<>(VehicleService.save(VehicleDto), HttpStatus.CREATED);
    }


    @GetMapping()
    public List<vehicleDto> getAllVehicles() {
        return VehicleService.findAllVehicles();
    }


    @GetMapping("/{id}")
    public vehicleDto getVehicleById(@PathVariable int id) {
        return VehicleService.findVehicleById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateVehicle(@PathVariable int id, @RequestBody vehicleDto VehicleDto) {
        // Lógica de actualización
        vehicleDto updatedVehicle = VehicleService.updateVehicle(id, VehicleDto);

        if (updatedVehicle != null) {
            return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Vehicle not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVehicle(@PathVariable int id) {
        boolean isDeleted = VehicleService.deleteVehicleById(id);

        if (isDeleted) {
            return new ResponseEntity<>("Vehicle deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Vehicle not found", HttpStatus.NOT_FOUND);
        }
    }

}