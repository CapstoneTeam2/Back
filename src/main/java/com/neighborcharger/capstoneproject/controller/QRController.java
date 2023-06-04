package com.neighborcharger.capstoneproject.controller;

import com.neighborcharger.capstoneproject.model.PrivateStation;
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

        } else{
            System.out.println("###################################처음!!!");
            model.addAttribute("RealStartTime", "몰라");
            model.addAttribute("RealEndTime", "몰라");
        }

        return "QRCodeResponeVIew";
    }
}