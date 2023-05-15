package com.neighborcharger.capstoneproject.model.chating;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageContentDTO {

    // 메시지 하나 관련 dto

    private Long messageId;
    private int senderId;
    private int receiverId;
    private LocalDateTime sendTime;
    private String messageContent;

}
