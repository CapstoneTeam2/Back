package com.neighborcharger.capstoneproject.controller;

import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @GetMapping("/price")
    public List<PrivateStation> rankingByPriceList(){
        List<PrivateStation> priceList = rankingService.rankingByPrice();
        return priceList;
    }

    @GetMapping("/score")
    public List<PrivateStation> rankingByScoreList(){
        List<PrivateStation> scoreList = rankingService.rankingByScore();
        return scoreList;
    }

}
