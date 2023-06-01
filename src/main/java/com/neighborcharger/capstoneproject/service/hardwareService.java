package com.neighborcharger.capstoneproject.service;

import com.neighborcharger.capstoneproject.DTO.ChargingCarDTO;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.Reservation_info;
import com.neighborcharger.capstoneproject.model.user.StationHardWare;
import com.neighborcharger.capstoneproject.model.user.UserEntity;
import com.neighborcharger.capstoneproject.repository.DB_Repository_private;
import com.neighborcharger.capstoneproject.repository.HardwareRepository;
import com.neighborcharger.capstoneproject.repository.Reservation_Repository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class hardwareService {

    @Autowired
    HardwareRepository hardwareRepository;

    @Autowired
    Reservation_Repository reservation_repository;

    @Autowired
    DB_Repository_private db_repository_private;

    public StationHardWare findstationHardWare(String reservaionPerson){
        StationHardWare stationHardWare = hardwareRepository.findBynickname(reservaionPerson).orElseGet(StationHardWare::new);
        return stationHardWare;
    }
    @Transactional
    public String startSetting(StationHardWare stationHardWare){

        LocalDateTime localDateTime = LocalDateTime.now();
        stationHardWare.setRealStartTime(localDateTime);
        System.out.println("#########"+localDateTime);
        hardwareRepository.save(stationHardWare);
        Reservation_info reservation_info = reservation_repository.findByreservationperson(stationHardWare.getNickname()).orElseGet(()->{
            return new Reservation_info();
        });
        reservation_info.setStationHardWare(stationHardWare);
        return "충전 시작";
    }

    @Transactional
    public void endCharging(StationHardWare stationHardWare){
        StationHardWare stationHardWare1 = stationHardWare;
        stationHardWare1.setChgerState("충전 끝");
    }
    @Transactional
    public ChargingCarDTO ChargingCar(UserEntity userEntity) throws ParseException {
        StationHardWare stationHardWare = new StationHardWare();

        for(Reservation_info reservation_info : userEntity.getReservations()){
            if(reservation_info.getStationHardWare().getChgerState().equals("충전중")){
                stationHardWare = reservation_info.getStationHardWare();
            }
        }
        ChargingCarDTO chargingCarDTO = new ChargingCarDTO();
        LocalDateTime localDateTime = LocalDateTime.now();
        Duration duration = Duration.between(stationHardWare.getRealStartTime(), localDateTime);
        PrivateStation privateStation = db_repository_private.findBystatNM(stationHardWare.getStatNM()).orElseGet(PrivateStation::new);
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        //System.out.println(stationHardWare.getRealStartTime());
        //System.out.println(localTime);

        String Runtime = hours + "시간 " + minutes + "분 " + seconds + "초";
        minutes += hours * 60;
        chargingCarDTO.setCost(CalCost(Integer.parseInt(privateStation.getPrice()), minutes, privateStation.getChgerType()));
        chargingCarDTO.setRuntime(Runtime);
        chargingCarDTO.setUsingElectric(CalElectric(minutes, 6));

        stationHardWare.setRealRunTime(Runtime);
        stationHardWare.setAmountElectricity(chargingCarDTO.getUsingElectric());
        stationHardWare.setCost(chargingCarDTO.getCost());

        return chargingCarDTO;
    }

    public double CalCost(int ChargingCost, long Min, String power){
        if(power.equals("02")) return ChargingCost * 6 * Min / 60.0 ;
        else  return ChargingCost * 50 * Min / 60.0;
    }
    public double CalElectric(long Min, int power){
        return Min * power / 60.0;
    }

    public void qrStart(PrivateStation privateStation) {
        LocalDateTime ldt = LocalDateTime.now();
        for(Reservation_info reservation_info : privateStation.getReservations()){
            boolean isBetween = reservation_info.getStart_time().isBefore(ldt) && ldt.isBefore(reservation_info.getEnd_time());
            if(isBetween){
                startSetting(reservation_info.getStationHardWare());
                break;
            }
        }
    }
}
