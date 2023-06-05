package com.neighborcharger.capstoneproject.controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neighborcharger.capstoneproject.model.Reservation_info;
import com.neighborcharger.capstoneproject.model.user.UserEntity;
import com.neighborcharger.capstoneproject.service.DB_Service;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.PublicStation;
import com.neighborcharger.capstoneproject.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@RestController
public class DB_Controller {
    @Autowired
    private DB_Service db_service;

    @Autowired
    UserService userService;

    @GetMapping("/User_Search_Token/{token}")
    public UserEntity userEntity(@PathVariable String token) {return db_service.search_Token(token);}

    @GetMapping("/Private_Station_get")
    public List<PrivateStation> privateStations() {return db_service.privateStationGet();}

    @GetMapping("/All_Station_get")
    public List<Object> allStation() {
        return db_service.findAllBychgerType();
    }

    @GetMapping("/Public_Station_get")
    public List<PublicStation> publicStation(){
        return db_service.publicStationGet();
    }

    @GetMapping("/filtter/{chgerType}")
    public List<PublicStation> publicStationList(@PathVariable String chgerType){
        return db_service.publicStationList(chgerType);
    }

    @GetMapping("/privateStat/isCharging")
    public List<PrivateStation> privateStatIsCharging(){
        return db_service.privateStatIsCharging();
    }

    @GetMapping("/Private_Station/myStation/{id}")
    public PrivateStation privateStationMy(@PathVariable String id){
        return db_service.privateStaionMy(id);
    }

    @GetMapping("/Private_Station/filtter/{Name}")
    public PrivateStation privateStation(@PathVariable String Name){
        return db_service.privateStation_fillter_get(Name);
    }

    @PostMapping("/DB_load")
    public String load_data() throws IOException, ParseException, org.apache.tomcat.util.json.ParseException {
        String result = "";
        URL url = new URL("https://apis.data.go.kr/B552584/EvCharger/getChargerInfo?serviceKey=yFIZ0L6Rg23XdWHkmPT3WkZhMx6%2BQWgX03quaNuFV0FdzG%2F0qjRtwMUKhnpzQjaeXb3glI32jQ3EZlB%2F9WnA1w%3D%3D&pageNo=1&numOfRows=9999&zscode=11590&dataType=JSON");
        BufferedReader bf;
        bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        result = bf.readLine();
        JsonNode jsonNode = new ObjectMapper().readTree(result);
        String info = jsonNode.get("items").get("item").toString();
        JSONArray jsonArray = new JSONArray(info);
        System.out.println("로그찍기");
        System.out.println(jsonArray.length());
        for(int i = 0; i < jsonArray.length(); i++){
                if (i % 24 != 0) continue;


                JSONObject jsonArray1 = (JSONObject) jsonArray.get(i);
                if(!jsonArray1.get("chgerId").equals("01"))
                    continue;

                PublicStation publicStation = new PublicStation();

                publicStation.setPublicStatKey(i);
                //publicStation.setAddr(jsonArray1.get("addr").toString());
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
                publicStation.setComponent("공용");
                System.out.println(jsonArray1.get("addr").toString());
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
        db_service.insertDB_private(privateStation);
        return "개인 충전기 등록";
    }

    @GetMapping("/StationReservation/{id}")
    public List<Reservation_info> StationReservation(@PathVariable String id){
        String username = userService.User_get(id).getNickname();
        PrivateStation privateStation = db_service.privateStationfindOwner(username);
        return privateStation.getReservations();
    }

    @GetMapping("/privateStat/{statNM}/{isOn}")
    public String isOnPrivateStat(@PathVariable String statNM, @PathVariable boolean isOn){
        PrivateStation privateStation = db_service.privateStation_fillter_get(statNM);
        privateStation.setOn(isOn);

        return "충전소 운영" + isOn;

    }
}