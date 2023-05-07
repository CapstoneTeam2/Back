package com.neighborcharger.capstoneproject.Service;

import com.neighborcharger.capstoneproject.model.PrivateStation;
import org.springframework.stereotype.Service;

@Service
public class User_Service {
    public String Request(PrivateStation privateStation){
        return "요청완료";
    }
}
