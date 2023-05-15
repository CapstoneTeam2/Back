package com.neighborcharger.capstoneproject.repository;

import com.neighborcharger.capstoneproject.model.PublicStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DB_Repository extends JpaRepository<PublicStation, Integer> {
    List<PublicStation> findAllBychgerType(String chgerType);
}
