package com.neighborcharger.capstoneproject.service;

import com.neighborcharger.capstoneproject.DTO.ChargingCarDTO;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.Reservation_info;
import com.neighborcharger.capstoneproject.model.user.StationHardWare;
import com.neighborcharger.capstoneproject.model.user.UserEntity;
import com.neighborcharger.capstoneproject.repository.DB_Repository_private;
import com.neighborcharger.capstoneproject.repository.HardwareRepository;
import com.neighborcharger.capstoneproject.repository.ReservationUserRepository;
import com.neighborcharger.capstoneproject.repository.Reservation_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class hardwareService {

    @Autowired
    HardwareRepository hardwareRepository;

    @Autowired
    Reservation_Repository reservation_repository;

    @Autowired
    DB_Repository_private db_repository_private;

    @Autowired
    ReservationUserRepository reservationUserRepository;

    @Autowired
    Reservation_Service reservation_service;

    @Autowired
    FirebaseCloudMessage_Service firebaseCloudMessageService;

    @Autowired
    UserService userService;

    @Autowired
    DB_Service db_service;

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
        System.out.println(stationHardWare.getChgerState());
        if(stationHardWare.getChgerState() == null) {
            chargingCarDTO.setCost(0);
            chargingCarDTO.setUsingElectric(0);
            chargingCarDTO.setRuntime("NO");

            return chargingCarDTO;
        }
        else {
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
            chargingCarDTO.setUsingElectric(CalElectric(minutes, privateStation.getChgerType()));

            stationHardWare.setRealRunTime(Runtime);
            stationHardWare.setAmountElectricity(chargingCarDTO.getUsingElectric());
            stationHardWare.setCost(chargingCarDTO.getCost());


            return chargingCarDTO;
        }
    }

    public double CalCost(int ChargingCost, long Min, String power){
        System.out.println(ChargingCost);
        System.out.println(power);
        if(power.equals("02")) return ChargingCost * 7 * Min / 60.0;
        else  return ChargingCost * 50 * Min / 60.0;
    }
    public double CalElectric(long Min, String power){
        if(power.equals("02")) return 7 * Min / 60.0;
        else  return 50 * Min / 60.0;
    }

    @Transactional
    public StationHardWare qrConnect(PrivateStation privateStation) {
        LocalDateTime ldt = LocalDateTime.now();
        Reservation_info reservation_info1 = new Reservation_info();

        System.out.println("###########3131그2 : 프라이빗 스테이션 문제? " + privateStation.getOwnerName());

        for(Reservation_info reservation_info : privateStation.getReservations()){
            boolean isBetween = reservation_info.getStart_time().isBefore(ldt) && ldt.isBefore(reservation_info.getEnd_time());
            if (isBetween) {
                reservation_info1 = reservation_info;
                break;
            }
        }

        UserEntity reservationPerson = reservationUserRepository.findBynickname(reservation_info1.getReservationperson()).orElse(null);

        if (reservation_info1.getStationHardWare() == null){ //처음 qr 찍었을 경우
            System.out.println(reservation_info1.getStatNM()+"###############################3");
            StationHardWare stationHardWare = new StationHardWare();
            stationHardWare.setChgerState("충전중");
            stationHardWare.setRealStartTime(ldt);
            System.out.println(reservation_info1.getReservationperson());
            System.out.println(reservationPerson.getNickname());
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
//                @Transactional
                public void run() {

                    //RealEndTime 업데이트
                    LocalDateTime now = LocalDateTime.now();
                    stationHardWare.setRealEndTime(now);

                    //Cost 비용 업데이트

                        Duration duration = Duration.between(stationHardWare.getRealStartTime(), now);

                        long hours = duration.toHours();
                        long minutes = duration.toMinutesPart();
                        long seconds = duration.toSecondsPart();

                        //System.out.println(stationHardWare.getRealStartTime());
                        //System.out.println(localTime);

                        String Runtime = hours + "시간 " + minutes + "분 " + seconds + "초";
                        minutes += hours * 60;
                        double cost = CalCost(Integer.parseInt(privateStation.getPrice()), minutes, privateStation.getChgerType());

                        double usingElectric = CalElectric(minutes, privateStation.getChgerType());

                        stationHardWare.setRealRunTime(Runtime);
                        stationHardWare.setAmountElectricity(usingElectric);
                        stationHardWare.setCost(cost);
                        System.out.println("비용 업데이트");

                    System.out.println(stationHardWare.getStatNM() + "****" + stationHardWare.getCost() + '원');
                    stationHardWare.setChgerState("충전끝");
                    System.out.println("충전 끝났지로오오오오옹###ㅏㅓ#ㅓ%ㅏ#ㅓ%ㅓ#%ㅏㅓ%");
                    try {
                        firebaseCloudMessageService.sendMessageTo2(reservationPerson.getFirebaseToken(), "이웃집 충전기", "충전이 완료되었습니다.", "응답");

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // MySQL에 변경된 객체를 저장
                    hardwareRepository.save(stationHardWare);
                    db_service.updateTotal(privateStation, cost, usingElectric, reservationPerson.getNickname());
                }
            };

            LocalDateTime startTime = reservation_info1.getStart_time();
            LocalDateTime endTime = reservation_info1.getEnd_time();

            Duration duration = Duration.between(startTime, endTime);
            long minutes = duration.toMinutes();
            timer.schedule(task, 60000 );

            stationHardWare.setNickname(reservation_info1.getReservationperson());
            stationHardWare.setCost(0);
            stationHardWare.setStatNM(privateStation.getStatNM());

            hardwareRepository.save(stationHardWare);
            reservation_info1.setStationHardWare(stationHardWare);
            return null;

        } else{ // 충전 중일 경우
            System.out.println(reservation_info1.getStatNM()+"######2222222#########################3");

           return reservation_service.findChargingPriceAndTime(privateStation);
        }


    }
}
