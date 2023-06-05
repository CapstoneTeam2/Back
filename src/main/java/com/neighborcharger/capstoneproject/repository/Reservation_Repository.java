package com.neighborcharger.capstoneproject.repository;

import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.Reservation_info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Reservation_Repository extends JpaRepository<Reservation_info, Integer> {
    Optional<Reservation_info> findByreservationperson(String reservationperson);
    List<Reservation_info> findBystatNM(String statNM);


}
