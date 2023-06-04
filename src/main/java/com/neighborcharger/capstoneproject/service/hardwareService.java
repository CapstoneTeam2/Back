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
//    @Transactional
//    public String startSetting(StationHardWare stationHardWare){
//
//        LocalDateTime localDateTime = LocalDateTime.now();
//        stationHardWare.setRealStartTime(localDateTime);
//        System.out.println("#########"+localDateTime);
//        hardwareRepository.save(stationHardWare);
//        Reservation_info reservation_info = reservation_repository.findByreservationperson(stationHardWare.getNickname()).orElseGet(Reservation_info::new);
//        reservation_info.setStationHardWare(stationHardWare);
//        return "충전 시작";
//    }

    @Transactional
    public void endCharging(StationHardWare stationHardWare){
        StationHardWare stationHardWare1 = stationHardWare;
        stationHardWare1.setChgerState("충전 끝");
    }
    @Transactional
    public ChargingCarDTO ChargingCar(UserEntity userEntity) throws ParseException {
        StationHardWare stationHardWare = new StationHardWare();

        for(Reservation_info reservation_info : userEntity.getReservations()){

            if(reservation_info.getStationHardWare()!= null && reservation_info.getStationHardWare().getChgerState().equals("충전중")){
                stationHardWare = reservation_info.getStationHardWare();
                break;
            }
        }
        ChargingCarDTO chargingCarDTO = new ChargingCarDTO();

        if(stationHardWare.getChgerState() == null) {
            chargingCarDTO.setCost(0);
            chargingCarDTO.setUsingElectric(0);
            chargingCarDTO.setRuntime("NO");

            return chargingCarDTO;
        }

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

    @Transactional
    public void qrStart(PrivateStation privateStation) {
        LocalDateTime ldt = LocalDateTime.now();
        Reservation_info reservation_info1 = new Reservation_info();
        StationHardWare stationHardWare = new StationHardWare();

        System.out.println("###########로그2 : 프라이빗 스테이션 문제? " + privateStation.getOwnerName());

        for(Reservation_info reservation_info : privateStation.getReservations()){
            boolean isBetween = reservation_info.getStart_time().isBefore(ldt) && ldt.isBefore(reservation_info.getEnd_time());
            if(isBetween){
                reservation_info1 = reservation_info;
                break;
            }
        }

        System.out.println("#############문제의 라인 전" + privateStation.getStatNM());

        stationHardWare.setChgerState("충전중");
        stationHardWare.setRealStartTime(ldt);
        stationHardWare.setNickname(reservation_info1.getReservationperson());
        stationHardWare.setCost(0);
        stationHardWare.setStatNM(privateStation.getStatNM());

        hardwareRepository.save(stationHardWare);
        reservation_info1.setStationHardWare(stationHardWare);
    }
}
