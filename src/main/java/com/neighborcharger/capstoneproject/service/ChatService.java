package com.neighborcharger.capstoneproject.service;

import com.neighborcharger.capstoneproject.model.base.BaseException;
import com.neighborcharger.capstoneproject.model.chating.MessageRoomsResDTO;
import com.neighborcharger.capstoneproject.model.chating.ParticipatingChatRoomAndUserDTOInterface;
import com.neighborcharger.capstoneproject.model.chating.SendMessageReqDTO;
import com.neighborcharger.capstoneproject.model.chating.SendMessageResDTO;
import com.neighborcharger.capstoneproject.model.chating.entity.ChatParticipantEntity;
import com.neighborcharger.capstoneproject.model.chating.entity.ChatRoomEntity;
import com.neighborcharger.capstoneproject.model.chating.entity.MessageEntity;
import com.neighborcharger.capstoneproject.model.user.UserEntity;
import com.neighborcharger.capstoneproject.repository.ChatParticipantRepository;
import com.neighborcharger.capstoneproject.repository.ChatRoomRepository;
import com.neighborcharger.capstoneproject.repository.MessageRepository;
import com.neighborcharger.capstoneproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    //이미 쪽지해본 사람에게 !
    public SendMessageResDTO sendMessage(SendMessageReqDTO sendMessageReqDTO) throws BaseException{

        //UserEntity 가져와
        UserEntity sendUser = getUserEntity(sendMessageReqDTO.getSenderId().intValue());
        UserEntity receiveUser = getUserEntity(sendMessageReqDTO.getReceiverId().intValue());

        ChatRoomEntity chatRoom = chatRoomRepository.getReferenceById(sendMessageReqDTO.getChatRoomId());

        System.out.println(sendMessageReqDTO.getMessageContent());

        //메시지 저장
        MessageEntity message = MessageEntity.builder()
                .messageContent(String.valueOf(sendMessageReqDTO.getMessageContent()))
                .senderId(sendUser)
                .receiverId(receiveUser)
                .chatRoom(chatRoomRepository.getReferenceById(sendMessageReqDTO.getChatRoomId()))
                .build();

        messageRepository.save(message);

        return new SendMessageResDTO(true, "상대방에게 메시지를 보냈습니다. ", chatRoom.getChatRoomId());

    }

    //메시지 처음 전송
    //새 채팅방 생성 후 그 채팅방에 저장
    public SendMessageResDTO sendFirstMessage(SendMessageReqDTO sendMessageReqDTO) throws BaseException{
        ChatRoomEntity chatRoom = ChatRoomEntity.builder().build();

        //새 채팅방 생성
        Long chatRoomId = chatRoomRepository.save(chatRoom).getChatRoomId();
        System.out.println(chatRoomId);
        log.info("채팅방 생성");

        //UserEntity 가져와
        UserEntity sendUser = getUserEntity(sendMessageReqDTO.getSenderId().intValue());
        UserEntity receiveUser = getUserEntity(sendMessageReqDTO.getReceiverId().intValue());
        log.info("UserEntity 잘 가져옴");

        //메시지 저장
        MessageEntity message = MessageEntity.builder()
                //.messageContent(sendMessageReqDTO.getMessageContent())
                .senderId(sendUser)
                .receiverId(receiveUser)
                .chatRoom(chatRoomRepository.getReferenceById(chatRoomId))
                .build();

        messageRepository.save(message);
        log.info("메시지 저장");

        //메시지 참가자 저장 -> 채팅 요청한 사람은 isChgerOwner 0 채팅 받는 사람은 isChgerOwner 1
        ChatParticipantEntity chatSenderParticipantEntity = ChatParticipantEntity.builder()
                .isChgerOwner(0)
                .chatRoom(chatRoomRepository.getReferenceById(chatRoomId))
                .chatParticipantId(sendUser)
                .build();

        ChatParticipantEntity chatReceiverParticipantEntity = ChatParticipantEntity.builder()
                .isChgerOwner(1)
                .chatRoom(chatRoomRepository.getReferenceById(chatRoomId))
                .chatParticipantId(receiveUser)
                .build();

        chatParticipantRepository.save(chatSenderParticipantEntity);
        chatParticipantRepository.save(chatReceiverParticipantEntity);

        return new SendMessageResDTO(true, "상대방에게 첫 메시지가 전송되었습니다.", chatRoomId);
    }

    public List<ParticipatingChatRoomAndUserDTOInterface> getParticatingChatRoomAndUserDTO(Long senderIdx) {
        return chatParticipantRepository.selectEveryParticipantInChatRoom(senderIdx);
    }


    private UserEntity getUserEntity(int userIdx) {
        UserEntity user = userRepository.selectByUserId(userIdx);
        return user;
    }

    public List<MessageRoomsResDTO> getUserRooms(int userIdx) throws BaseException {

        List<ChatParticipantEntity> otherUserRooms = chatParticipantRepository.selectEveryOtherUserInUserChatRoom(userIdx);
        List<MessageRoomsResDTO> responseDTOList = new ArrayList<>();
        for(ChatParticipantEntity otherUserRoom : otherUserRooms) {

            MessageEntity message = messageRepository.findFirst1ByChatRoom_ChatRoomIdOrderBySentTimeDesc(otherUserRoom.getChatRoom().getChatRoomId());
            UserEntity otherUser = otherUserRoom.getChatParticipantId();
            MessageRoomsResDTO dto = MessageRoomsResDTO.builder()
                    .currentMessage(message.getMessageContent())
                    .currentMessageTime(message.getSentTime())
                    .chatRoomId(otherUserRoom.getChatRoom().getChatRoomId())
                    .build();
            responseDTOList.add(dto);
        }
        return responseDTOList;
    }

}
