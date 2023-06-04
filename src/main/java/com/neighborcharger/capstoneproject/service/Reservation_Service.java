package com.neighborcharger.capstoneproject.service;

import com.neighborcharger.capstoneproject.DTO.PredictResDTO;
import com.neighborcharger.capstoneproject.model.user.StationHardWare;
import com.neighborcharger.capstoneproject.model.user.UserEntity;
import com.neighborcharger.capstoneproject.repository.DB_Repository_private;
import com.neighborcharger.capstoneproject.repository.HardwareRepository;
import com.neighborcharger.capstoneproject.repository.ReservationUserRepository;
import com.neighborcharger.capstoneproject.repository.Reservation_Repository;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.Reservation_info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Reservation_Service {
    @Autowired
    Reservation_Repository reservation_repository;
    @Autowired
    DB_Repository_private db_repository_private;

    @Autowired
    HardwareRepository hardwareRepository;

    @Autowired
    ReservationUserRepository reservationUserRepository;

    @Transactional
    public void Time_Reservation_service(Reservation_info reservation_info, String name){
        PrivateStation privateStation = db_repository_private.findBystatNM(name).orElseGet(PrivateStation::new);

        reservation_info.setChecking("대기");
        System.out.println("################"+privateStation.getStatNM());
        System.out.println("################"+reservation_info.getStatNM());
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


//         privateStation.getReservations().remove(reservation_info);
//         reservation_repository.deleteById(Math.toIntExact(reservation_info.getId()));

        reservation_info.setChecking("거절");
    }

    @Transactional
    public void updateReservationStat(Reservation_info reservation_info, String resp){
        Reservation_info reservationInfo = reservation_info;
        reservationInfo.setChecking(resp);

    }

    public List<Pair<String, String>> reservationLists(String stationName) {
        PrivateStation privateStation = db_repository_private.findBystatNM(stationName).orElseGet(PrivateStation::new);
        List<Pair<String, String>> list = new ArrayList<>();
        for(Reservation_info reservationInfo : privateStation.getReservations()){
            Pair<String, String> pair = Pair.of(reservationInfo.getStart_time().toString(), reservationInfo.getEnd_time().toString());

            list.add(pair);
        }
        return list;
    }

    @Transactional // 한 사용자의 예약 내역 보기
    public List<Reservation_info> myReservationList(String nickname){
        UserEntity user = reservationUserRepository.findBynickname(nickname)
                .orElseGet(UserEntity::new);

        List<Reservation_info> myReservations = user.getReservations();
        return myReservations;
    }

    public PredictResDTO prediccostandtime(double capacity, int percent, int cost, String Power){
        double power = Double.parseDouble(Power);
        double charging = capacity * percent / 100;
        PredictResDTO predictResDTO = new PredictResDTO();
        double Times = charging / power;
        System.out.println(capacity);
        System.out.println(percent);
        System.out.println(cost);

        System.out.println("Times : " + Times);
        double preCost = Times * cost;
        int our = (int) (charging / power);
        int min = (int) (charging % power);
        String.valueOf(preCost);
        String preTime = our + "시간 " + min + "분";
        System.out.println(preTime);
        predictResDTO.setPrediccost(String.valueOf(preCost));
        predictResDTO.setPredictime(preTime);
        System.out.println(predictResDTO);
        return predictResDTO;
    }


    // 충전소 이름으로 예약 내역에서 예약자를 찾아내고
    // 예약자 이름이나 관련 정보로 하드웨어에서 충전 중 가격, 충전 중 시간 받기
    public StationHardWare findChargingPriceAndTime(PrivateStation privateStation){
        LocalDateTime ldt = LocalDateTime.now();
        Reservation_info findReservation = new Reservation_info();

        for(Reservation_info reservation_info : privateStation.getReservations()){
            boolean isBetween = reservation_info.getStart_time().isBefore(ldt) && ldt.isBefore(reservation_info.getEnd_time());
            if(isBetween) {
                findReservation = reservation_info;
                break;
            }
        }

        String reservationPersonName = findReservation.getReservationperson();

        StationHardWare isChargingInform =  hardwareRepository.findBynickname(reservationPersonName).get();

        return isChargingInform;


    }
}
