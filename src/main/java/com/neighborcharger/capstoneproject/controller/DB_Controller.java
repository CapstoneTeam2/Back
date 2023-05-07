package com.neighborcharger.capstoneproject.controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neighborcharger.capstoneproject.Service.DB_Service;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.PublicStation;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

@RestController
public class DB_Controller {
    @Autowired
    private DB_Service db_service;


    @GetMapping("/Public_Station_get")
    public List<PublicStation> publicStation(){
        return db_service.publicStationGet();
    }

    @GetMapping("/filtter/{chgerType}")
    public List<PublicStation> publicStationList(@PathVariable String chgerType){
        return db_service.publicStationList(chgerType);
    }

    @PostMapping("/DB_load")
    public String load_data() throws IOException, ParseException, org.apache.tomcat.util.json.ParseException {
        String result = "";
        URL url = new URL("https://apis.data.go.kr/B552584/EvCharger/getChargerInfo?serviceKey=yFIZ0L6Rg23XdWHkmPT3WkZhMx6%2BQWgX03quaNuFV0FdzG%2F0qjRtwMUKhnpzQjaeXb3glI32jQ3EZlB%2F9WnA1w%3D%3D&pageNo=1&numOfRows=30&dataType=JSON");
        BufferedReader bf;
        bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        result = bf.readLine();
        JsonNode jsonNode = new ObjectMapper().readTree(result);
        String info = jsonNode.get("items").get("item").toString();
        JSONArray jsonArray = new JSONArray(info);

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonArray1 = (JSONObject) jsonArray.get(i);
            PublicStation publicStation = new PublicStation();

            publicStation.setPublicStatKey(i);
            publicStation.setAddr(jsonArray1.get("addr").toString());
            publicStation.setBnm(jsonArray1.get("bnm").toString());
            publicStation.setChgerId(jsonArray1.get("chgerId").toString());
            publicStation.setChgerType(jsonArray1.get("chgerType").toString());
            publicStation.setMethod(jsonArray1.get("method").toString());
            publicStation.setOutput(jsonArray1.get("output").toString());
            publicStation.setParkingFree(jsonArray1.get("parkingFree").toString());
            publicStation.setNowTsdt(jsonArray1.get("nowTsdt").toString());
            publicStation.setNote(jsonArray1.get("note").toString());
            publicStation.setStatNM(jsonArray1.get("statNm").toString());
            publicStation.setUseTime(jsonArray1.get("useTime").toString());
            publicStation.setStat(jsonArray1.get("stat").toString());
            publicStation.setStatUpdDt(jsonArray1.get("statUpdDt").toString());

            //publicStation.setAddr(jsonArray1.get("addr").toString());
            db_service.insertDB(publicStation);
        }
        /*
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse();
        JSONObject items = (JSONObject)jsonObject.get("items");
        JSONArray jsonArray = (JSONArray) items.get("item");
        System.out.println(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonArray1 = (JSONObject) jsonArray.get(i);
            PublicStation publicStation = new PublicStation();

            publicStation.setPublicStatKey(i);
            publicStation.setAddr(jsonArray1.get("addr").toString());
            publicStation.setBnm(jsonArray1.get("bnm").toString());
            publicStation.setChgerId(jsonArray1.get("chgerId").toString());
            publicStation.setChgerType(jsonArray1.get("chgerType").toString());
            publicStation.setMethod(jsonArray1.get("method").toString());
            publicStation.setOutput(jsonArray1.get("output").toString());
            publicStation.setParkingFree(jsonArray1.get("parkingFree").toString());
            publicStation.setNowTsdt(jsonArray1.get("nowTsdt").toString());
            publicStation.setNote(jsonArray1.get("note").toString());
            publicStation.setStatNM(jsonArray1.get("statNm").toString());
            publicStation.setUseTime(jsonArray1.get("useTime").toString());
            publicStation.setStat(jsonArray1.get("stat").toString());
            publicStation.setStatUpdDt(jsonArray1.get("statUpdDt").toString());

            //publicStation.setAddr(jsonArray1.get("addr").toString());
            db_service.insertDB(publicStation);
        }*/
        return "공용 충전기 업데이트";
    }

    @PostMapping("/Register_Station")
    public String Register_Station(@RequestBody PrivateStation privateStation){
        db_service.insertDB(privateStation);
        return "개인 충전기 등록";
    }
}