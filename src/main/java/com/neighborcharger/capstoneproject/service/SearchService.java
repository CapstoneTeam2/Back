package com.neighborcharger.capstoneproject.service;

import com.neighborcharger.capstoneproject.DTO.KeywordSearchReqDTO;
import com.neighborcharger.capstoneproject.DTO.KeywordSearchResDTO;
import com.neighborcharger.capstoneproject.DTO.StationDTO;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.PublicStation;
import com.neighborcharger.capstoneproject.repository.DB_Repository;
import com.neighborcharger.capstoneproject.repository.DB_Repository_private;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private DB_Repository_private db_repository_private;

    @Autowired
    private DB_Repository db_repository;

    @Transactional
    public List<Object> searchStatNM(String keyword){
        List<PrivateStation> privateStatList = db_repository_private.findBystatNMContaining(keyword);
        List<PublicStation> publicStatList = db_repository.findBystatNMContaining(keyword);
//      List<StationDTO> correctStatList = new ArrayList<>();
        List<Object> searchStationList = new ArrayList<>();
        searchStationList.addAll(privateStatList);
        searchStationList.addAll(publicStatList);
//        if (privateStatList.isEmpty() && publicStatList.isEmpty()){
//            return correctStatList;
//        }
//
//        for (PrivateStation privateStat : privateStatList){
//            correctStatList.add(this.convertPrivateEntityToDTO(privateStat));
//        }
//        for (PublicStation publicStat : publicStatList){
//            correctStatList.add(this.convertPublicEntityToDTO(publicStat));
//        }
//
//        return correctStatList;
        return searchStationList;
    }

    private StationDTO convertPrivateEntityToDTO(PrivateStation privateStation){
        return StationDTO.builder()
                .isPublicStat(false)
                .statNM(privateStation.getStatNM())
                .addr(privateStation.getAddr())
                .build();
    }

    private StationDTO convertPublicEntityToDTO(PublicStation publicStation){
        return StationDTO.builder()
                .isPublicStat(true)
                .statNM(publicStation.getStatNM())
                .addr(publicStation.getAddr())
                .build();
    }

}
