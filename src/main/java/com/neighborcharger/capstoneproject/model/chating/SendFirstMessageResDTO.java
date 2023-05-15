package com.neighborcharger.capstoneproject.model.chating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendFirstMessageResDTO {
    private boolean isCreated;
    private String resultMessage;

    private Long chatRoomId;
}
