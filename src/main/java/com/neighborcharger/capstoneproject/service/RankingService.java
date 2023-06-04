package com.neighborcharger.capstoneproject.service;

import com.neighborcharger.capstoneproject.DTO.RankingPrivateStatDTO;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.repository.DB_Repository_private;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class RankingService {

    @Autowired
    private DB_Repository_private db_repository_private;

    @Transactional
    public List<PrivateStation> rankingByPrice(){ //가격은 오름차순 평점은 내림차순
        List<PrivateStation> allSortedList = db_repository_private
                .findAll(Sort.by(Sort.Direction.ASC, "price"));

        System.out.println("이제 리스트 나와야돼");
        System.out.println(allSortedList);

        List<PrivateStation> sortedByPricePrivateStatList = new ArrayList<>();

        for (PrivateStation privateStation : allSortedList){
            if (privateStation.getPrice() == "0"|| privateStation.getPrice() == null){
                continue;
            }
            sortedByPricePrivateStatList.add(privateStation);
        }
        return sortedByPricePrivateStatList;
    }

    @Transactional
    public List<PrivateStation> rankingByScore(){ //가격은 오름차순 평점은 내림차순
        List<PrivateStation> allSortedList = db_repository_private
                .findAll(Sort.by(Sort.Direction.DESC, "score"));

        List<PrivateStation> sortedByScorePrivateStatList = new ArrayList<>();

        for (PrivateStation privateStation : allSortedList){
            if (privateStation.getScore() == 0){
                continue;
            }
            sortedByScorePrivateStatList.add(privateStation);
        }
        return sortedByScorePrivateStatList;
    }

}
