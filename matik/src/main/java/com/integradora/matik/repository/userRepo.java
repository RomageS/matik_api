package com.integradora.matik.repository;

import com.integradora.matik.Entity.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepo extends JpaRepository<userEntity, Integer> {
    boolean existsByEmail(String email);

    Optional<userEntity> findByEmail(String email);
}
