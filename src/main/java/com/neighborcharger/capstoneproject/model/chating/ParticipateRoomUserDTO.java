package com.neighborcharger.capstoneproject.model.chating;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipateRoomUserDTO {

    // 현채 채팅 참여 중인 유저 아이디와 채팅방 아이디를 가짐

    private Long chatRoomId;
    private Long participatingUserIdx;
}

