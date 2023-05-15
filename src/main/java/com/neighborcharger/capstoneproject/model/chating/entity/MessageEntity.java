package com.neighborcharger.capstoneproject.model.chating.entity;

import com.neighborcharger.capstoneproject.model.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@DynamicInsert
@Table(name="Message")
@Entity(name="Message")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", name ="message_id")
    private Long messageId;

    @Column(name ="message_content")
    private String messageContent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sender_id")
    private UserEntity senderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="receiver_id")
    private UserEntity receiverId;

    @Column(name = "send_time")
    private LocalDateTime sentTime;

    @ManyToOne
    @JoinColumn(name= "chat_room_id")
    private ChatRoomEntity chatRoom ;
}
