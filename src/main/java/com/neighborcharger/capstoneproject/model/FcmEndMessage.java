package com.neighborcharger.capstoneproject.model;
import com.google.api.client.util.Data;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import com.neighborcharger.capstoneproject.model.Reservation_info;
import javax.persistence.Column;
import java.util.Map;

@Builder
@AllArgsConstructor
@Getter
public class FcmEndMessage {
    private boolean validate_only;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private Data data;
        private Notification notification;
        private String token;
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
        private String    cost;
        private String    times;
    }
}
