package com.neighborcharger.capstoneproject.service;

import com.google.api.client.util.DateTime;
import com.neighborcharger.capstoneproject.repository.DB_Repository_private;
import com.neighborcharger.capstoneproject.repository.Reservation_Repository;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.Reservation_info;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class Reservation_Service {
    @Autowired
    Reservation_Repository reservation_repository;
    @Autowired
    DB_Repository_private db_repository_private;

    @Transactional
    public void Time_Reservation_service(Reservation_info reservation_info, String name){
        PrivateStation privateStation = db_repository_private.findBystatNM(name).orElseThrow(()->{
            return new IllegalArgumentException("");
        });
        reservation_info.setChecking("대기");
        privateStation.getReservations().add(reservation_info);
        reservation_repository.save(reservation_info);
    }

    @Transactional
    public void ReservationReject(String ReservationPerson, String owner){
        PrivateStation privateStation = db_repository_private.findByownerName(owner).orElseGet(()->{
            return new PrivateStation();
        });
        Reservation_info reservation_info = reservation_repository.findByreservationperson(ReservationPerson).orElseGet(()->{
            return new Reservation_info();
        });
        reservation_info.setChecking("거절");
    }

    @Transactional
    public void updateReservationStat(Reservation_info reservation_info, String resp){
        Reservation_info reservationInfo = reservation_info;
        reservationInfo.setChecking(resp);
    }

    public List<LocalTime> reservationLists(String stationName) {
        PrivateStation privateStation = db_repository_private.findBystatNM(stationName).orElseGet(PrivateStation::new);
        List<LocalTime> list = new ArrayList<>();
        for(Reservation_info reservationInfo : privateStation.getReservations()){
            list.add(reservationInfo.getStart_time());
            list.add(reservationInfo.getEnd_time());
        }
        return list;
    }
}
