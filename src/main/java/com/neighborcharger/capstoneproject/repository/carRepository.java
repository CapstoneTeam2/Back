package com.neighborcharger.capstoneproject.repository;

import com.neighborcharger.capstoneproject.model.PublicStation;
import com.neighborcharger.capstoneproject.model.car;
import com.neighborcharger.capstoneproject.model.user.StationHardWare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface carRepository extends JpaRepository<car, Integer> {
    Optional<car> findByname(String carname);
}
