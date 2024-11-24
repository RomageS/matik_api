package com.integradora.matik.service;

import com.integradora.matik.Entity.vehicleEntity;
import com.integradora.matik.dto.vehicleDto;
import com.integradora.matik.repository.vehicleRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class vehicleService {



    private final vehicleRepo VehicleRepo;

    public vehicleService(vehicleRepo VehicleRepo) {
        this.VehicleRepo = VehicleRepo;
    }

    public Integer save(vehicleDto VehicleDto) {

        vehicleEntity VehicleEntity = dtoToEntity(VehicleDto);
        return VehicleRepo.save(VehicleEntity).getId();
    }

    // Método para obtener todos los vehiculos y convertirlos a DTO
    public List<vehicleDto> findAllVehicles() {
        return VehicleRepo.findAll().stream()
                .map(this::entityToDto) // Convierte cada entidad a DTO
                .collect(Collectors.toList());
    }

    // Método para buscar un vehiculo por ID y convertirlo a DTO
    public vehicleDto findVehicleById(Integer id) {
        Optional<vehicleEntity> optionalUser = VehicleRepo.findById(id);
        return optionalUser.map(this::entityToDto).orElse(null); // Devuelve DTO o null si no se encuentra
    }

    private vehicleEntity dtoToEntity(vehicleDto VehicleDto) {
        return vehicleEntity.builder()
                .withId(VehicleDto.getId())
                .withBrand(VehicleDto.getBrand())
                .withModel(VehicleDto.getModel())
                .withYear(VehicleDto.getYear())
                .withColor(VehicleDto.getColor())
                .withTransmission(VehicleDto.getTransmission())
                .withPriceDay(VehicleDto.getPriceDay())
                .withMileage(VehicleDto.getMileage())
                .withStatus(VehicleDto.getStatus())
                .build();
    }

    // Conversión de entidad a DTO
    private vehicleDto entityToDto(vehicleEntity VehicleEntity) {
        return vehicleDto.builder()
                .withId(VehicleEntity.getId())
                .withBrand(VehicleEntity.getBrand())
                .withModel(VehicleEntity.getModel())
                .withYear(VehicleEntity.getYear())
                .withColor(VehicleEntity.getColor())
                .withTransmission(VehicleEntity.getTransmission())
                .withPriceDay(VehicleEntity.getPriceDay())
                .withMileage(VehicleEntity.getMileage())
                .withStatus(VehicleEntity.getStatus())
                .build();
    }

    public vehicleDto updateVehicle(Integer id, vehicleDto vehicleDto) {
        // Buscar el vehículo por ID
        Optional<vehicleEntity> optionalVehicle = VehicleRepo.findById(id);

        if (optionalVehicle.isPresent()) {
            // Actualizar los campos de la entidad existente con los valores del DTO
            vehicleEntity existingVehicle = optionalVehicle.get();

            existingVehicle.setBrand(vehicleDto.getBrand());
            existingVehicle.setModel(vehicleDto.getModel());
            existingVehicle.setYear(vehicleDto.getYear());
            existingVehicle.setColor(vehicleDto.getColor());
            existingVehicle.setTransmission(vehicleDto.getTransmission());
            existingVehicle.setPriceDay(vehicleDto.getPriceDay());
            existingVehicle.setMileage(vehicleDto.getMileage());
            existingVehicle.setStatus(vehicleDto.getStatus());

            // Guardar los cambios en la base de datos
            vehicleEntity updatedVehicle = VehicleRepo.save(existingVehicle);

            // Convertir la entidad actualizada a DTO y devolverla
            return entityToDto(updatedVehicle);
        }

        // Si no se encuentra el vehículo, retornar null o lanzar una excepción
        return null;
    }

    public boolean deleteVehicleById(Integer id) {
        Optional<vehicleEntity> optionalVehicle = VehicleRepo.findById(id);

        if (optionalVehicle.isPresent()) {
            VehicleRepo.deleteById(id);
            return true; // Indica que el vehículo fue eliminado exitosamente
        }

        return false; // Indica que no se encontró el vehículo con el ID especificado
    }

}