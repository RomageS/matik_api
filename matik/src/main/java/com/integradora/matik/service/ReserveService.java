package com.integradora.matik.service;

import com.integradora.matik.dto.ReserveDto;
import com.integradora.matik.Entity.ReserveEntity;
import com.integradora.matik.Entity.userEntity;
import com.integradora.matik.Entity.vehicleEntity;
import com.integradora.matik.repository.ReserveRepo;
import com.integradora.matik.repository.userRepo;
import com.integradora.matik.repository.vehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReserveService {

    private final ReserveRepo reserveRepo;
    private final userRepo userRepo;
    private final vehicleRepo vehicleRepo;

    @Autowired
    public ReserveService(ReserveRepo reserveRepo, userRepo userRepo, vehicleRepo vehicleRepo) {
        this.reserveRepo = reserveRepo;
        this.userRepo = userRepo;
        this.vehicleRepo = vehicleRepo;
    }

    public Integer createReservation(ReserveDto reserveDto) {
        System.out.println("Datos recibidos en createReservation: " + reserveDto); // Debug

        // Validaciones explícitas
        if (reserveDto.getVehicleId() == null) {
            throw new IllegalArgumentException("El ID del vehículo no puede ser nulo.");
        }

        if (reserveDto.getUserId() == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo.");
        }

        userEntity user = userRepo.findById(reserveDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        vehicleEntity vehicle = vehicleRepo.findById(reserveDto.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado."));

        ReserveEntity reserveEntity = dtoToEntity(reserveDto);
        reserveEntity.setUser(user);
        reserveEntity.setVehicle(vehicle);

        return reserveRepo.save(reserveEntity).getId();
    }




    public List<ReserveDto> getAllReservations() {
        return reserveRepo.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public ReserveDto getReservationById(Integer id) {
        ReserveEntity reserveEntity = reserveRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada."));
        return entityToDto(reserveEntity);
    }

    public ReserveDto updateReservation(Integer id, ReserveDto reserveDto) {
        ReserveEntity reserveEntity = reserveRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada."));

        userEntity user = userRepo.findById(reserveDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        vehicleEntity vehicle = vehicleRepo.findById(reserveDto.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado."));

        reserveEntity.setFechaEntrega(reserveDto.getFechaEntrega());
        reserveEntity.setFechaRegreso(reserveDto.getFechaRegreso());
        reserveEntity.setUser(user);
        reserveEntity.setVehicle(vehicle);

        return entityToDto(reserveRepo.save(reserveEntity));
    }

    public boolean deleteReservation(Integer id) {
        if (reserveRepo.existsById(id)) {
            reserveRepo.deleteById(id);
            return true;
        }
        return false;
    }

    private ReserveEntity dtoToEntity(ReserveDto reserveDto) {
        userEntity user = userRepo.findById(reserveDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        vehicleEntity vehicle = vehicleRepo.findById(reserveDto.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado.")); // Evita el error

        return ReserveEntity.builder()
                .withId(reserveDto.getId())
                .withFechaEntrega(reserveDto.getFechaEntrega())
                .withFechaRegreso(reserveDto.getFechaRegreso())
                .withVehicle(vehicleRepo.findById(reserveDto.getVehicleId())
                        .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado")))
                .withUser(userRepo.findById(reserveDto.getUserId())
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado")))
                .build();
    }

    private ReserveDto entityToDto(ReserveEntity reserveEntity) {
        return ReserveDto.builder()
                .withId(reserveEntity.getId())
                .withFechaEntrega(reserveEntity.getFechaEntrega())
                .withFechaRegreso(reserveEntity.getFechaRegreso())
                .withUserId(reserveEntity.getUser().getId())
                .withVehicleId(reserveEntity.getVehicle().getId())
                .build();
    }

}
