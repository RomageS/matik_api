package com.integradora.matik.controller;

import com.integradora.matik.dto.dateDto;
import com.integradora.matik.service.dateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dates")
@CrossOrigin
public class dateController {

    private final dateService DateService;

    @Autowired
    public dateController(dateService DateService) {
        this.DateService = DateService;
    }

    @PostMapping
    public ResponseEntity<Object> saveDate(@RequestBody Map<String, Object> reserva) {
        try {
            // Procesa el objeto recibido
            System.out.println("Datos recibidos: " + reserva);

            // Lógica para guardar la reserva en la base de datos

            return new ResponseEntity<>("Reserva creada exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la reserva", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public List<dateDto> findAllDates() {
        return DateService.findAllDates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findDateById(@PathVariable int id) {
        try {
            dateDto date = DateService.findDateById(id);
            return new ResponseEntity<>(date, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDate(@PathVariable int id, @RequestBody dateDto dateDto) {
        try {
            dateDto updatedDate = DateService.updateDate(id, dateDto);
            return new ResponseEntity<>(updatedDate, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar la fecha", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDateById(@PathVariable int id) {
        boolean isDeleted = DateService.deleteDateById(id);
        if (isDeleted) {
            return new ResponseEntity<>("Fecha eliminada correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Fecha no encontrada", HttpStatus.NOT_FOUND);
        }
    }
}

