package com.neighborcharger.capstoneproject.Service;

import com.neighborcharger.capstoneproject.Repository.DB_Repository;
import com.neighborcharger.capstoneproject.Repository.DB_Repository_private;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.PublicStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public void insertDB(PrivateStation privateStation) {
        db_repository_private.save(privateStation);
    }

    @Transactional
    public List<PublicStation> publicStationGet(){
        List<PublicStation> publicStationList  = db_repository.findAll();

        return publicStationList;
    }
    @Transactional
    public List<PublicStation> publicStationList(String chgerType) {
        List<PublicStation> publicStationList  = db_repository.findAllBychgerType(chgerType);

        return publicStationList;
    }
}
