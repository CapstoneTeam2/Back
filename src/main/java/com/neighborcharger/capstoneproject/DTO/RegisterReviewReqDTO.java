package com.neighborcharger.capstoneproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterReviewReqDTO { // 리뷰 등록 시 서버가 프론트로 받는 것

    String text; // 리뷰 내용
    int score; // 별점 (1 ~ 5)
    String reviewerFirebaseToken; // 리뷰쓴 사람 파베 토큰
    String ownerFirebaseToken; // 충전소 주인 파베 토큰

}

