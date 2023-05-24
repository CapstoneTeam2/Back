package com.neighborcharger.capstoneproject.model;
import com.google.api.client.util.Data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmResponesMessage {
    private boolean validate_only;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private Notification notification;
        private String token;
        private Data data;
    }
    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }
    @Builder
    @AllArgsConstructor
    @Getter
    public static class Data{
        private String    returnToken;
        private String    startTime;
        private String    endTime;
        private String    checking;
    }
}
