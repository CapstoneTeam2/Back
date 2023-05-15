package com.neighborcharger.capstoneproject.service;

import com.neighborcharger.capstoneproject.repository.DB_Repository;
import com.neighborcharger.capstoneproject.repository.DB_Repository_private;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.PublicStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class DB_Service {
    @Autowired
    private DB_Repository db_repository;
    @Autowired
    private DB_Repository_private db_repository_private;
    @Transactional
    public void insertDB(PublicStation publicStation) {
        db_repository.save(publicStation);
    }

    @Transactional
    public void insertDB_private(PrivateStation privateStation) {
        db_repository_private.save(privateStation);
    }

    @Transactional
    public List<PublicStation> publicStationGet(){
        List<PublicStation> publicStationList  = db_repository.findAll();

        return publicStationList;
    }
    @Transactional
    public List<PrivateStation> privateStationGet(){
        List<PrivateStation> privateStations  = db_repository_private.findAll();

        return privateStations;
    }

    @Transactional
    public List<Object> findAllBychgerType() {
        List<Object> resultList = new ArrayList<>();

        List<PublicStation> publicStations = db_repository.findAll();
        resultList.addAll(publicStations);

        List<PrivateStation> privateStations = db_repository_private.findAll();
        resultList.addAll(privateStations);

        return resultList;
    }

    @Transactional
    public List<PublicStation> publicStationList(String chgerType) {
        List<PublicStation> publicStationList  = db_repository.findAllBychgerType(chgerType);

        return publicStationList;
    }
}
