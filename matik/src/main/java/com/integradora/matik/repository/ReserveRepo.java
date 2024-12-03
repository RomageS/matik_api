package com.integradora.matik.repository;

import com.integradora.matik.Entity.ReserveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveRepo extends JpaRepository<ReserveEntity, Integer> {
    List<ReserveEntity> findAll(); // Este método devuelve todas las reservas
    List<ReserveEntity> findByUserId(Integer userId); // Buscar reservas por usuario
    List<ReserveEntity> findByVehicleId(Integer vehicleId); // Buscar reservas por vehículo
}
