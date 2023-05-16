package com.neighborcharger.capstoneproject.model.chating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRoomsResDTO {
    private Long chatRoomId;
    private int otherUserIdx;
    private String otherUserNickname;
    private String currentMessage;
    private LocalDateTime currentMessageTime;
}
