package com.neighborcharger.capstoneproject.controller;

import com.neighborcharger.capstoneproject.DTO.StationDTO;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.ReviewEntity;
import com.neighborcharger.capstoneproject.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/keyword") // 키워드로 충전소명 뽑기
    public List<Object> keywordSearch(@RequestParam(value = "keyword") String keyword){
        List<Object> correctStatList = searchService.searchStatNM(keyword);
        System.out.println(correctStatList);
        return correctStatList;
    }

}
