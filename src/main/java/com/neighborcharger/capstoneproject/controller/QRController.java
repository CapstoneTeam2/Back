package com.neighborcharger.capstoneproject.controller;

import com.amazonaws.services.ec2.model.Reservation;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.Reservation_info;
import com.neighborcharger.capstoneproject.model.user.StationHardWare;
import com.neighborcharger.capstoneproject.service.DB_Service;
import com.neighborcharger.capstoneproject.service.Reservation_Service;
import com.neighborcharger.capstoneproject.service.UserService;
import com.neighborcharger.capstoneproject.service.hardwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class QRController {
    @Autowired
    hardwareService hardwareservice;

    @Autowired
    DB_Service db_service;

    @Autowired
    UserService userService;

    @Autowired
    Reservation_Service reservation_service;


    @GetMapping("/QRstationinfomation/{stationName}")
    private String QRStart(@PathVariable String stationName, Model model){
        PrivateStation privateStation = db_service.privateStation_fillter_get(stationName);

        System.out.println("#############로그 : " + stationName + "########큐알 코드 후");

        StationHardWare statHW = hardwareservice.qrConnect(privateStation);
        System.out.println("다녀옴!####################################");
        if(statHW != null){ // 지금 충전 중이라면
            System.out.println("다왔다#################################");
            model.addAttribute("RealRunTime", statHW.getRealRunTime());
            model.addAttribute("cost", statHW.getCost());

        } else{ //충전 중이 아니라면 (처음 큐알을 찍는거라면) -> 이미 qrConnect에서 충전중이라고 디비에서는 바뀜
            System.out.println("###################################처음!!!");

            // 선택된 개인충전소의 예약 목록에서 충전중인 것을 찾고, 그것의 endTime을 가져오자
            Reservation_info findReservationInfo = new Reservation_info();
            List<Reservation_info> reservationInfoList = privateStation.getReservations();

            System.out.println(reservationInfoList.size() + ":::::::: 예약 개수");


            for(Reservation_info reservation_info : reservationInfoList){
                System.out.println(reservation_info.getEnd_time() + "ㅁㅁㅁㅁㅁㅁㅁ끝나는 시간ㅁ 예약정보 하나");
                System.out.println(reservation_info.getChecking() + "::::::이게 나와야되는데?");
                if (reservation_info.getChecking().equals("대기") || reservation_info.getChecking().equals("수락")){
                    System.out.println(reservation_info.getStart_time() + "***** 이때 시작함");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                    String formattedTime = reservation_info.getEnd_time().format(formatter);
                    model.addAttribute("end_time", formattedTime);
                    System.out.println(reservation_info.getEnd_time() + "시간이 여깃어용");
                }
            }


        }

        return "QRCodeResponeVIew";
    }
}