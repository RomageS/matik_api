package com.integradora.matik.repository;

import com.integradora.matik.Entity.vehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface vehicleRepo extends JpaRepository<vehicleEntity, Integer> {
}
