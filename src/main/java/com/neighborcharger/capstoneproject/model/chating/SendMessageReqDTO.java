package com.neighborcharger.capstoneproject.model.chating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageReqDTO {
    
    private Long chatRoomId;
    private String messageContent;
    private Long senderId;
    private Long receiverId;

}
