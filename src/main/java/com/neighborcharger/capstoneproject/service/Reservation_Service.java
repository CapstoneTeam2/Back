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
        PrivateStation privateStation = db_repository_private.findByownerName(owner).orElseGet(PrivateStation::new);
        Reservation_info reservation_info = reservation_repository.findByreservationperson(ReservationPerson).orElseGet(Reservation_info::new);


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
        double charging = capacity * percent / 100;
        System.out.println(charging);
        double power = 0.0;
        PredictResDTO predictResDTO = new PredictResDTO();
        hardwareService Hws = new hardwareService();
        if (Power.equals("02")) power = 7;
        else power = 50.0;

        double Times = charging / power;
        System.out.println(capacity);
        System.out.println(percent);
        System.out.println(cost);

        System.out.println("Times : " + Times);
        double preCost = charging * cost;
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
        System.out.println("이게 무슨 충전소?");
        System.out.println(privateStation.getStatNM());

        for(Reservation_info reservation_info : privateStation.getReservations()){
            boolean isBetween = reservation_info.getStart_time().isBefore(ldt) && ldt.isBefore(reservation_info.getEnd_time());
            if(isBetween) {
                findReservation = reservation_info;
                System.out.println("이거 안나오면 안된거");
                break;
            }
        }
        System.out.println("####여기가 문제인듯####");
        String reservationPersonName = findReservation.getReservationperson();
        System.out.println("니가문제냐?##################"+reservationPersonName);
        StationHardWare isChargingInform =  hardwareRepository.findBynickname(reservationPersonName).get();
        System.out.println("너냐???#############"+isChargingInform.getRealStartTime());
        return isChargingInform;
    }

    public List<Reservation_info> stationReservation(String name){
        PrivateStation privateStation = db_repository_private.findBystatNM(name).orElseGet(PrivateStation::new);

        return privateStation.getReservations();
    }
}
