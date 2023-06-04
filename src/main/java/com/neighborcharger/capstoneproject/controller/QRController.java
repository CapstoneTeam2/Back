package com.neighborcharger.capstoneproject.controller;

import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.service.DB_Service;
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


    @GetMapping("/QRstationinfomation/{stationName}")
    private String QRStart(@PathVariable String stationName){
        PrivateStation privateStation = db_service.privateStation_fillter_get(stationName);

        System.out.println("#############로그 : " + stationName + "########큐알 코드 후");

        hardwareservice.qrStart(privateStation);

        return "QRCodeResponeVIew";
    }
}