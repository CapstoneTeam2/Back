package com.neighborcharger.capstoneproject.repository;

import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.Reservation_info;
import com.neighborcharger.capstoneproject.model.user.StationHardWare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HardwareRepository extends JpaRepository<StationHardWare, Integer> {
    Optional<StationHardWare> findBynickname(String nickname);
}
