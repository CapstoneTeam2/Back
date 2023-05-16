package com.neighborcharger.capstoneproject.model.chating.entity;

import com.neighborcharger.capstoneproject.model.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;


@DynamicInsert
@Table(name="ChatParticipant")
@Entity(name="ChatParticipant")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatParticipantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", name = "chat_participant_column_id")
    private Long chatParticipantColumnId;

    @ManyToOne
    @JoinColumn(name = "chat_participant_id")
    private UserEntity chatParticipantId;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoomEntity chatRoom;

    @Column(columnDefinition = "INT",name="is_chger_owner")
    private int isChgerOwner;



}
