package com.neighborcharger.capstoneproject.service;

import com.neighborcharger.capstoneproject.repository.DB_Repository_private;
import com.neighborcharger.capstoneproject.repository.Reservation_Repository;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.Reservation_info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Reservation_Service {
    @Autowired
    Reservation_Repository reservation_repository;
    @Autowired
    DB_Repository_private db_repository_private;

    public void Time_Reservation_service(Reservation_info reservation_info, String name){
        PrivateStation privateStation = db_repository_private.findBystatNM(name).orElseThrow(()->{
            return new IllegalArgumentException("");
        });
        privateStation.getReservations().add(reservation_info);
        reservation_repository.save(reservation_info);
    }

    public void Reservation_Delete(String ReservationPerson, String owner){
        PrivateStation privateStation = db_repository_private.findByownerName(owner).orElseGet(()->{
            return new PrivateStation();
        });
        Reservation_info reservation_info = reservation_repository.findByreservationperson(ReservationPerson).orElseGet(()->{
            return new Reservation_info();
        });
        privateStation.getReservations().remove(reservation_info);
        reservation_repository.deleteById(Math.toIntExact(reservation_info.getId()));
    }
}

