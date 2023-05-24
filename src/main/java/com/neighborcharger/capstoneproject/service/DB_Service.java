package com.neighborcharger.capstoneproject.service;

import com.neighborcharger.capstoneproject.model.user.UserEntity;
import com.neighborcharger.capstoneproject.repository.DB_Repository;
import com.neighborcharger.capstoneproject.repository.DB_Repository_private;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.PublicStation;
import com.neighborcharger.capstoneproject.repository.ReservationUserRepository;
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

    @Autowired
    private ReservationUserRepository reservationUserRepository;

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
    public UserEntity search_Token(String token){
        UserEntity userEntity = reservationUserRepository.findByfirebaseToken(token).orElseGet(()->{
            return new UserEntity();
        });
        return userEntity;
    }

    @Transactional
    public PrivateStation privateStaionMy(String id) {
        UserEntity userOwner = reservationUserRepository.findByid(id)
                .orElseGet(() -> {
                    return new UserEntity();
                });
        System.out.println("**(*(*(잘되나" + userOwner.getNickname());

        PrivateStation privateStationMyInform = db_repository_private.findByfirebaseToken(userOwner.getFirebaseToken()).orElseGet(
                () -> {
                    return new PrivateStation();
                });
        System.out.println("**(*(*(잘되나2222" + privateStationMyInform.getOwnerName());

        return privateStationMyInform;
    }

    @Transactional
    public PrivateStation privateStation_fillter_get(String name){
        PrivateStation privateStation = db_repository_private.findBystatNM(name).orElseGet(()->{
            return new PrivateStation();
        });

        return privateStation;
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