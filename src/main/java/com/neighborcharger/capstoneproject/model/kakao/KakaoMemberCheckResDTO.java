package com.neighborcharger.capstoneproject.model.kakao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoMemberCheckResDTO {
    private Boolean isMember; //회원이 맞는지
    private String resultMessage; //결과 메시지
}
