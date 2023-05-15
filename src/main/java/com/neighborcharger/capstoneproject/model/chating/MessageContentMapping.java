package com.neighborcharger.capstoneproject.model.chating;

import java.time.LocalDateTime;

public interface MessageContentMapping {

    Long getMessageId();
    String getMessageContent();
    MessageContent getSenderId();
    MessageContent getReceiverId();
    LocalDateTime getSentTime();
    ChatRoomId getChatRoom();

    interface MessageContent{
        int getUserIdx();
    }

    interface  ChatRoomId{
        int getChatRoomId();
    }
}
