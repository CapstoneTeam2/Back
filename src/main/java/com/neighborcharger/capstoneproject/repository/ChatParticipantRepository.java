package com.neighborcharger.capstoneproject.repository;

import com.neighborcharger.capstoneproject.model.chating.ParticipatingChatRoomAndUserDTOInterface;
import com.neighborcharger.capstoneproject.model.chating.entity.ChatParticipantEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipantEntity, Long> {

    //참가해있는 채팅방과 참가자 목록 전부 불러오기
    @Query(value = "SELECT ChatParticipant.chat_room_id as chatRoomId,\n" +
            "               ChatParticipant.chat_participant_id as participatingUserIdx\n" +
            "        FROM ChatParticipant\n" +
            "        INNER JOIN (SELECT chat_room_id FROM ChatParticipant WHERE chat_participant_id = :userIdx) as CPcRI\n" +
            "            ON CPcRI.chat_room_id = ChatParticipant.chat_room_id", nativeQuery = true)
    List<ParticipatingChatRoomAndUserDTOInterface> selectEveryParticipantInChatRoom(@Param("userIdx") Long userIdx);


    // 입력된 idx를 가진 특정 사용자와 동일한 방에 참여하는 다른 사용자들의 목록
    @Query(value = "SELECT distinct p\n"+
            "FROM ChatParticipant n \n"+
            "INNER JOIN ChatParticipant p\n"+
            "ON n.chatParticipantId.userIdx = :userIdx\n"+
            "WHERE p.chatRoom = n.chatRoom AND p.chatParticipantId.userIdx <> :userIdx")
    List<ChatParticipantEntity> selectEveryOtherUserInUserChatRoom(int userIdx);
}
