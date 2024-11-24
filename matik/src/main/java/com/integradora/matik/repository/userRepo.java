package com.integradora.matik.repository;

import com.integradora.matik.Entity.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepo extends JpaRepository<userEntity, Integer> {
}
