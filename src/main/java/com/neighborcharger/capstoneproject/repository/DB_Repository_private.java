package com.neighborcharger.capstoneproject.repository;

import com.neighborcharger.capstoneproject.model.PrivateStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DB_Repository_private extends JpaRepository<PrivateStation, Integer> {
    Optional<PrivateStation> findBystatNM(String statNM);
    Optional<PrivateStation> findByownerName(String ownerName);
    Optional<PrivateStation> findByfirebaseToken(String firebaseToken);


}