//package com.neighborcharger.capstoneproject.service;
//
//import com.neighborcharger.capstoneproject.model.base.BaseException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.ProtocolException;
//import java.net.URL;
//import java.util.Map;
//
//import static com.neighborcharger.capstoneproject.model.base.BaseResponseStatus.*;
//
//@Log4j2
//@Service
//public class KakaoService {
//
//    public long checkKakaoUser(String token) throws BaseException{
//        String reqURL = "https://kapi.kakao.com/v2/user/me";
//        Map<String, Object> resMap = null;
//        ObjectMapper mapper = new ObjectMapper();
//
//        //kakao_access_token 으로 사용자 유효한지 확인
//        try{
//            URL url = new URL(reqURL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//            conn.setRequestProperty("Authorization", "Bearer " + token);
//
//            int responseCode = conn.getResponseCode();
//            if(responseCode != 200){
//                log.error("만료되거나 유효하지 않은 KAKAO ACCESS TOKEN 입니다.");
//                throw new BaseException(INVALID_ACCESS_KAKAO);
//            }
//
//            InputStream inputStream = conn.getInputStream();
//            resMap = mapper.readValue(inputStream, Map.class);	//결과 받아옴.
//
//
//            try{
//                long kakaoIdx = Long.parseLong(String.valueOf(resMap.get("id")));
//                return kakaoIdx;
//            }catch (NumberFormatException e){
//                //long aa= Long.parseLong("2516106831");
//
//                log.error(resMap.get("id"));
//                log.error(e);
//            }
//
//
//        } catch (MalformedURLException |ProtocolException e) {
//            log.error(e);
//        } catch (IOException e) {
//            log.error(e);
//        }
//        return 0;
//    }
//
//}
//
//
//
//
///**
// *
// *     public static void insertLog(HttpServletRequest request, String memberId, String publicDataPk) {
// *         String url = "http://localhost:8080/logs";
// *
// *         AsyncRestTemplate restTemplate = new AsyncRestTemplate(); // 비동기 전달
// *         HttpHeaders httpHeaders = new HttpHeaders();
// *         httpHeaders.setContentType(MediaType.APPLICATION_JSON);
// *
// *         JSONObject jsonObject = new JSONObject();
// *
// *         jsonObject.put("memberId", memberId);
// *         jsonObject.put("sessionId", request.getRequestedSessionId());
// *         jsonObject.put("publicDataId", publicDataId);
// *
// *         HttpEntity<String> logRequest = new HttpEntity<>(jsonObject.toString(), httpHeaders);
// *         restTemplate.postForEntity(url, logRequest, String.class);
// *
// *         ... 생략
// *     }
// */
//
