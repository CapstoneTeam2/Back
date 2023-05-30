package com.neighborcharger.capstoneproject.service;

import com.neighborcharger.capstoneproject.DTO.KeywordSearchReqDTO;
import com.neighborcharger.capstoneproject.DTO.KeywordSearchResDTO;
import com.neighborcharger.capstoneproject.repository.DB_Repository_private;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;

@Service
public class SearchService {

    @Autowired
    private DB_Repository_private db_repository_private;

//    @Transactional
//    public KeywordSearchResDTO keywordSearch(@RequestBody KeywordSearchReqDTO keywordSearchReqDTO){
//
//    }
}
