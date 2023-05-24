package com.neighborcharger.capstoneproject.controller;

import com.neighborcharger.capstoneproject.DTO.Respone_DTO;
import com.neighborcharger.capstoneproject.service.FirebaseCloudMessage_Service;
import com.neighborcharger.capstoneproject.service.Reservation_Service;
import com.neighborcharger.capstoneproject.model.Reservation_info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ReservationController {
    @Autowired
    private FirebaseCloudMessage_Service firebaseCloudMessageService;

    @Autowired
    private Reservation_Service reservationService;

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
    private String Time_Reservation(@RequestBody Reservation_info reservation_info, @PathVariable String TargetToken, @PathVariable String returnToken) throws IOException {
        System.out.println(TargetToken);
        System.out.println(reservation_info);

        reservationService.Time_Reservation_service(reservation_info, reservation_info.getStatNM());
        //firebaseCloudMessageService.sendMessageTo(TargetToken, "이웃집 충전기", "충전기 예약이 있어요!", returnToken, reservation_info.getStart_time(), reservation_info.getEnd_time(), "예약");
        return "시간 저장 -> 요청 보냄";
    }

    @PostMapping("/Reservation_Respone")
    private String Reservation_Respone(@RequestBody Respone_DTO respone_dto) throws IOException {
        if(respone_dto.getChoice() == 1) {
            firebaseCloudMessageService.sendMessageTo2(respone_dto.getToken(), "이웃집 충전기", "요청이 수락되었어요", "응답");
        }
        else {
            firebaseCloudMessageService.sendMessageTo2(respone_dto.getToken(), "이웃집 충전기", "요청이 거절되었어요", "응답");
        }
        return "수락/거절 응답 완료";
    }
    @DeleteMapping("/Delete_Reservation/{ReservationPerson}/{ownerName}") // 예약자
    private String Delete_Reservation(@PathVariable String ReservationPerson, @PathVariable String ownerName){
        reservationService.Reservation_Delete(ReservationPerson, ownerName);
        return "거절을 받음";
    }
}