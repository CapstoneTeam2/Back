package com.neighborcharger.capstoneproject.Repository;

import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.Reservation_info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Reservation_Repository extends JpaRepository<Reservation_info, Integer> {
}
