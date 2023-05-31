package com.neighborcharger.capstoneproject.controller;

import com.google.api.client.util.DateTime;
import com.neighborcharger.capstoneproject.DTO.ReservationDTO;
import com.neighborcharger.capstoneproject.DTO.Respone_DTO;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.user.UserEntity;
import com.neighborcharger.capstoneproject.repository.ReservationUserRepository;
import com.neighborcharger.capstoneproject.service.DB_Service;
import com.neighborcharger.capstoneproject.service.FirebaseCloudMessage_Service;
import com.neighborcharger.capstoneproject.service.Reservation_Service;
import com.neighborcharger.capstoneproject.model.Reservation_info;
import com.neighborcharger.capstoneproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class ReservationController {
    @Autowired
    private FirebaseCloudMessage_Service firebaseCloudMessageService;

    @Autowired
    private Reservation_Service reservationService;

    @Autowired
    UserService userService;

    @Autowired
    DB_Service db_service;
    /*@GetMapping("/Request")
    private String Request_Charge() throws IOException {
        firebaseCloudMessageService.sendMessageTo(
                "eu6apZpDSNafi7I_-wkqE-:APA91bFOpclseZl_hqUaEc13c4xvZ4N16eL85L9Lft-31OG5r8pwOECH61HDzy9hRieGT8kUh41SJsaArei0fgmQGk5u6rnjEdiPqlOkrN1d70pyzQhNC5e24v3x4d-IZkJfPxKd6sFf",
                "이웃집 충전기",
                "충전기 예약이 있습니다.");
        return "전기차 충전요청";
    }*/

    /*@GetMapping("/Response/{FCM_Token}/{choice}")
    private String Response_Charge(@PathVariable String FCM_Token, @PathVariable int choice) throws IOException {
        if(choice == 1)firebaseCloudMessageService.sendMessageTo(FCM_Token, "이웃집 충전기", "요청이 수락되었습니다.");
        else firebaseCloudMessageService.sendMessageTo(FCM_Token, "이웃집 충전기", "거절되었습니다.");
        return "수락/거절 응답 완료";
    }*/

    @PostMapping("/Time_Reservation/{TargetToken}/target/{returnToken}")
    private String Time_Reservation(@RequestBody ReservationDTO reservationDTO, @PathVariable String TargetToken, @PathVariable String returnToken) throws IOException {
        Reservation_info reservation_info = new Reservation_info();
        reservation_info.setStatNM(reservationDTO.getStationName());
        reservation_info.setReservationperson(reservationDTO.getReservationPerson());
        reservation_info.setChecking("대기");
        // "yyyy-MM-dd HH:mm:ss.SSS" 형식의 문자열
        System.out.println("################################################################");
        System.out.println("##################"+reservationDTO.getStarttime()+"###########");
        System.out.println("##################"+reservationDTO.getEndtime());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime startTime = LocalDateTime.parse(reservationDTO.getStarttime(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(reservationDTO.getEndtime(), formatter);

        System.out.println("##################################################################");
        System.out.println("##################"+reservationDTO.getStarttime()+"###########");
        System.out.println("##################"+reservationDTO.getEndtime());

        reservation_info.setStart_time(startTime);
        reservation_info.setEnd_time(endTime);

        reservationService.Time_Reservation_service(reservation_info, reservationDTO.getStationName());
        userService.insertReservation(returnToken, reservation_info);
        firebaseCloudMessageService.sendMessageTo(TargetToken, "이웃집 충전기", "충전기 예약이 있어요!", returnToken, reservation_info.getStart_time().toString(), reservation_info.getEnd_time().toString(), "예약");

        return "시간 저장 -> 요청 보냄";
    }

    @PostMapping("/Reservation_Respone")
    private String Reservation_Respone(@RequestBody Respone_DTO respone_dto) throws IOException {
        if(respone_dto.getChoice() == 1) {
            firebaseCloudMessageService.sendMessageTo2(respone_dto.getToken(), "이웃집 충전기", "요청이 수락되었어요", "응답");
            PrivateStation privateStation = db_service.privateStation_fillter_get(respone_dto.getStation_name());
            for(Reservation_info reservation_info : privateStation.getReservations()){
                if(reservation_info.getStatNM().equals(respone_dto.getStation_name())){
                    reservationService.updateReservationStat(reservation_info, "수락");
                    userService.ReservationUpdate(respone_dto.getName(),respone_dto.getStation_name(),"수락");
                }
            }
        }
        else {
            firebaseCloudMessageService.sendMessageTo2(respone_dto.getToken(), "이웃집 충전기", "요청이 거절되었어요", "응답");
            PrivateStation privateStation = db_service.privateStation_fillter_get(respone_dto.getStation_name());
            for(Reservation_info reservation_info : privateStation.getReservations()){
                if(reservation_info.getStatNM().equals(respone_dto.getStation_name())){
                    reservationService.updateReservationStat(reservation_info, "거절");
                    userService.ReservationUpdate(respone_dto.getName(),respone_dto.getStation_name(),"거절");
                }
            }
        }
        return "수락/거절 응답 완료";
    }

    @GetMapping("/ReservationTimeInfo/{stationName}") // 이 충전소의 예약 되어 있는 시간 GET
    public List<LocalDateTime> reservationList(@PathVariable String stationName){
        List<LocalDateTime> dateTimes = reservationService.reservationLists(stationName);
        return dateTimes;
    }

    @GetMapping("/Reservation/myList/{nickname}")
    public List<Reservation_info> myReservationList (@PathVariable String nickname){
        List<Reservation_info> result = reservationService.myReservationList(nickname);
        return result;
    }

}
