package com.neighborcharger.capstoneproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import com.neighborcharger.capstoneproject.model.FcmAcceptMessage;
import com.neighborcharger.capstoneproject.model.FcmEndMessage;
import com.neighborcharger.capstoneproject.model.FcmMessage;
import com.neighborcharger.capstoneproject.model.FcmResponesMessage;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Component
@RequiredArgsConstructor
@Builder
public class FirebaseCloudMessage_Service {
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/capstoenteam2/messages:send";
    // "https://fcm.googleapis.com/v1/projects/fcm-server-b7e93/messages:send";
    private final ObjectMapper objectMapper;
    public String formatDate(String input) {
        LocalDateTime dateTime = LocalDateTime.parse(input, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 HH:mm");
        return dateTime.format(formatter);
    }

    public String EndDate(String input) {
        LocalDateTime dateTime = LocalDateTime.parse(input, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(formatter);
    }
    public void sendMessageTo(String targetToken, String title, String body, String returnToken, String startTime, String endTime, String checking, String address) throws IOException {
        String message = makeMessage(targetToken, title, body, returnToken, startTime, endTime, checking, address);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request)
                .execute();

        System.out.println(response.body().string());
    }
    private String makeMessage(String targetToken, String title, String body, String returnToken, String startTime, String endTime, String checking, String address) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body + " " + formatDate(startTime) + "~" + EndDate(endTime))
                                .image(null)
                                .build()
                        ).data(
                                FcmMessage.Data.builder()
                                        .returnToken(returnToken)
                                        //.startTime(startTime)
                                        .endTime(endTime)
                                        .checking(checking)
                                        .address(address)
                                        .build()
                        )
                        .build()
                )
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }

    public void sendMessageTo2(String targetToken, String title, String body, String checking) throws IOException {
        String message = makeMessage2(targetToken, title, body, checking);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request)
                .execute();

        System.out.println(response.body().string());
    }

    private String makeMessage2(String targetToken, String title, String body, String checking) throws JsonProcessingException {
        FcmResponesMessage fcmMessage = FcmResponesMessage.builder()
                .message(FcmResponesMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmResponesMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        ).data(
                                FcmResponesMessage.Data.builder()
                                        .checking(checking)
                                        .build()
                        )
                        .build()
                )
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }

    public void sendEndMessage(String targetToken, String title, String body, String checking, String Cost, String Times) throws IOException {
        String message = makeEndMessage(targetToken, title, body, checking, Cost, Times);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request)
                .execute();

        System.out.println(response.body().string());
    }

    private String makeEndMessage(String targetToken, String title, String body, String checking, String Cost, String Times) throws JsonProcessingException {
        FcmEndMessage fcmEndMessage = FcmEndMessage.builder()
                .message(FcmEndMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmEndMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        ).data(
                                FcmEndMessage.Data.builder()
                                        .checking(checking)
                                        .cost(Cost)
                                        .times(Times)
                                        .build()
                        )
                        .build()
                )
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmEndMessage);
    }

    public void  acceptMessage(String targetToken, String title, String body, String checking, String address) throws IOException {
        String message = makeAcceptMessage(targetToken, title, body, checking, address);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request)
                .execute();

        System.out.println(response.body().string());
    }

    private String makeAcceptMessage(String targetToken, String title, String body, String checking, String address) throws JsonProcessingException {
        FcmAcceptMessage fcmAcceptMessage = FcmAcceptMessage.builder()
                .message(FcmAcceptMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmAcceptMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        ).data(
                                FcmAcceptMessage.Data.builder()
                                        .checking(checking)
                                        .address(address)
                                        .build()
                        )
                        .build()
                )
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmAcceptMessage);
    }
    public String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/capstoenteam2-firebase-adminsdk-dn63g-55a95679b3.json";
                //"firebase/fcm-server-b7e93-firebase-adminsdk-76g95-ae1f10b55b.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}