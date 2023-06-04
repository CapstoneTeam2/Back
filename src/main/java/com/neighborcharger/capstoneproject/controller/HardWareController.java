package com.neighborcharger.capstoneproject.controller;

import com.neighborcharger.capstoneproject.DTO.ChargingCarDTO;
import com.neighborcharger.capstoneproject.DTO.ChargingStationDTO;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.Reservation_info;
import com.neighborcharger.capstoneproject.model.user.StationHardWare;
import com.neighborcharger.capstoneproject.model.user.UserEntity;
import com.neighborcharger.capstoneproject.repository.ReservationUserRepository;
import com.neighborcharger.capstoneproject.service.DB_Service;
import com.neighborcharger.capstoneproject.service.UserService;
import com.neighborcharger.capstoneproject.service.hardwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.ParseException;

@RestController
public class HardWareController {
    @Autowired
    hardwareService hardwareservice;

    @Autowired
    DB_Service db_service;

    @Autowired
    UserService userService;

//    @PostMapping("/StartSetting") // 충전 시작
//    private String StartSetting(@RequestBody StationHardWare stationHardWare){
//        hardwareservice.startSetting(stationHardWare);
//        return "시작!";
//    }

//    @GetMapping("/QRstationinfomation/{stationName}")
//    private String QRStart(@PathVariable String stationName){
//        PrivateStation privateStation = db_service.privateStation_fillter_get(stationName);
//
//        System.out.println("#############로그 : " + stationName + "########큐알 코드 후");
//
//        hardwareservice.qrStart(privateStation);
//
//        return "QRCodeResponeVIew";
//    }

    @GetMapping("/ChargingCar/{id}") // 충전중 이용자에게 줄 정보
    private ChargingCarDTO ChargingCar(@PathVariable String id) throws ParseException {
        UserEntity userEntity = userService.User_get(id);
        ChargingCarDTO chargingCarDTO = hardwareservice.ChargingCar(userEntity);
        return chargingCarDTO;
    }

//    @GetMapping("/ChargingStation/{nickname}")
//    private ChargingStationDTO ChargingStation(){
//        ChargingStationDTO chargingStationDTO = hardwareservice.ChargingStation(nickname);
//        return chargingStationDTO;
//    }

    @PostMapping("/EndCharging/{nickname}/{stationName}") // 충전 종료시
    private String ChargingEnd(@PathVariable String nickname, @PathVariable String stationName){
        PrivateStation privateStation = db_service.privateStation_fillter_get(stationName);
        StationHardWare stationHardWare = hardwareservice.findstationHardWare(nickname);
        // PrivateStation 에 전체 사용 전력량, 충전시간 추가

        double totalCost = privateStation.getTotalcost() + stationHardWare.getCost();
        double totalEle = privateStation.getTotalelectric() + stationHardWare.getAmountElectricity();

        db_service.updateTotal(privateStation, totalCost, totalEle, nickname);
        hardwareservice.endCharging(stationHardWare);

        return "충전 끝";
    }
}