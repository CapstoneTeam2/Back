package com.neighborcharger.capstoneproject.controller;

import com.neighborcharger.capstoneproject.model.user.StationHardWare;
import com.neighborcharger.capstoneproject.service.hardwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@RestController
public class HardWareController {
    @Autowired
    hardwareService hardwareservice;

    @PostMapping("/StartSetting")
    private String StartSetting(@RequestBody StationHardWare stationHardWare){
        hardwareservice.startSetting(stationHardWare);
        return "시작!";
    }
}