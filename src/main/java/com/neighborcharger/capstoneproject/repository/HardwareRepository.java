package com.neighborcharger.capstoneproject.repository;

import com.neighborcharger.capstoneproject.model.Reservation_info;
import com.neighborcharger.capstoneproject.model.user.StationHardWare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HardwareRepository extends JpaRepository<StationHardWare, Integer> {
}
