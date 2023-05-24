package com.neighborcharger.capstoneproject.service;

import com.neighborcharger.capstoneproject.model.user.StationHardWare;
import com.neighborcharger.capstoneproject.repository.HardwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class hardwareService {

    @Autowired
    HardwareRepository hardwareRepository;

    public String startSetting(StationHardWare stationHardWare){
        hardwareRepository.save(stationHardWare);
        return "충전 시작";
    }
}
