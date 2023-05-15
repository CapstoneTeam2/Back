package com.neighborcharger.capstoneproject.controller;

import com.neighborcharger.capstoneproject.model.base.BaseException;
import com.neighborcharger.capstoneproject.model.base.BaseResponse;
import com.neighborcharger.capstoneproject.model.chating.*;
import com.neighborcharger.capstoneproject.service.ChatService;
import com.neighborcharger.capstoneproject.service.JwtService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api")
public class ChatController {
    private final JwtService jwtService;
    private final ChatService chatService;

    public ChatController(JwtService jwtService, ChatService chatService) {
        this.jwtService = jwtService;
        this.chatService = chatService;
    }

    @PostMapping("/chat/send")
    public BaseResponse<SendMessageResDTO> sendMessage(@RequestBody SendMessageReqDTO sendMessageReqDTO){
        try{

            // 기존에 쪽지 했는지 확인
            if(sendMessageReqDTO.getChatRoomId()==null || sendMessageReqDTO.getChatRoomId() == 0){
                //기존 쪽지방 있는지 화인

                boolean alreadyHaveRoom = false;

                //senderUser가 참가해있는 채팅방과 참가자 목록
                List<ParticipatingChatRoomAndUserDTOInterface> userParticipatingRoomAndUserList;
                userParticipatingRoomAndUserList = chatService.getParticatingChatRoomAndUserDTO(sendMessageReqDTO.getSenderId());


                for (ParticipatingChatRoomAndUserDTOInterface eachUserInChatRoom : userParticipatingRoomAndUserList) {
                    log.info("채팅방 목록의 다른 유저 = " + eachUserInChatRoom.getParticipatingUserIdx() );

                    //기존에 있던 채팅방의 채팅 상대가 이번에 메시지를 보내려는 상대방이라면
                    if(eachUserInChatRoom.getParticipatingUserIdx().equals(sendMessageReqDTO.getReceiverId())){
                        alreadyHaveRoom = true;
                        sendMessageReqDTO.setChatRoomId(eachUserInChatRoom.getChatRoomId());
                        break;
                    }
                }

                //이미 방이 있다면 그 방에 보내고 결과 전송
                if(alreadyHaveRoom) {
                    log.info("이미 방이 있음");
                    return new BaseResponse<>(chatService.sendMessage(sendMessageReqDTO));
                }
                //방이 없다면 방을 생성하고 그 방에 결과 전송
                else{
                    log.info("방이 없어서 새로 생성하고 쪽지 보냄");
                    return new BaseResponse<>(chatService.sendFirstMessage(sendMessageReqDTO));
                }

            }
            jwtService.validateAccessToken(sendMessageReqDTO.getSenderId().intValue());
            return new BaseResponse<>(chatService.sendMessage(sendMessageReqDTO));
        }catch (BaseException e){
            log.error(" API : api/chat/send" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/room")
    public BaseResponse<List<MessageRoomsResDTO>> getUserRooms(@RequestParam("userIdx") int id) {
        try {
            return new BaseResponse<>(chatService.getUserRooms(id));
        } catch (BaseException e) {
            log.error(" API : api/room" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }

//    @GetMapping("chat/room")
//    public BaseResponse<RoomMessageDTO> getMessages(@RequestParam("roomId") Long roomId){
//        try{
//            return new BaseResponse<>(chatService.getRoomMessages(roomId));
//        } catch(BaseException e){
//            log.error(" API : api/room" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
//            return new BaseResponse<>(e.getStatus());
//        }
//    }
}
