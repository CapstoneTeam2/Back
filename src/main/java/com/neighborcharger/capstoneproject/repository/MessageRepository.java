package com.neighborcharger.capstoneproject.repository;

import com.neighborcharger.capstoneproject.model.chating.MessageContentMapping;
import com.neighborcharger.capstoneproject.model.chating.entity.ChatRoomEntity;
import com.neighborcharger.capstoneproject.model.chating.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    List<MessageContentMapping> findByChatRoom(ChatRoomEntity chatRoom);
    MessageEntity findFirst1ByChatRoom_ChatRoomIdOrderBySentTimeDesc(Long chatRoomId);

}
