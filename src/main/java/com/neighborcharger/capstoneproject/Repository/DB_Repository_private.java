package com.neighborcharger.capstoneproject.Repository;

import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.PublicStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DB_Repository_private extends JpaRepository<PrivateStation, Integer> {
    Optional<PrivateStation> findBystatNM(String statNM);
}