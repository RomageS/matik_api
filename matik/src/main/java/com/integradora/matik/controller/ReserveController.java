package com.integradora.matik.controller;

import com.integradora.matik.dto.ReserveDto;
import com.integradora.matik.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@CrossOrigin
public class ReserveController {

    private final ReserveService reserveService;

    @Autowired
    public ReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @PostMapping
    public ResponseEntity<Object> createReservation(@RequestBody ReserveDto reserveDto) {
        System.out.println("Datos recibidos en el backend: " + reserveDto); // Debug en el backend
        try {
            Integer reservationId = reserveService.createReservation(reserveDto);
            return new ResponseEntity<>(reservationId, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al crear la reserva.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/all")
    public ResponseEntity<List<ReserveDto>> getAllReservations() {
        List<ReserveDto> reservations = reserveService.getAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getReservationById(@PathVariable int id) {
        try {
            ReserveDto reserveDto = reserveService.getReservationById(id);
            return new ResponseEntity<>(reserveDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Reserva no encontrada.", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateReservation(@PathVariable int id, @RequestBody ReserveDto reserveDto) {
        try {
            ReserveDto updatedReservation = reserveService.updateReservation(id, reserveDto);
            return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar la reserva.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable int id) {
        if (reserveService.deleteReservation(id)) {
            return new ResponseEntity<>("Reserva eliminada con éxito.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Reserva no encontrada.", HttpStatus.NOT_FOUND);
        }
    }
}
