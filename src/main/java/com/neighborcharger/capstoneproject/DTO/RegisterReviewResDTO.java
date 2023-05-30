package com.neighborcharger.capstoneproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterReviewResDTO { // 리뷰 등록시, 프론트에게 줄 것

    private String resultMessage; // ex. 리뷰가 등록되었습니다.

    private String reviewerNickname; // 리뷰쓴 사람 닉네임 (id로 할까?)
    private int score; // 방금 쓴 사람이 작성한 별점
    private String text; // 리뷰 내용
    private int reviewIdx; // 리뷰 인덱스 느낌 (고유식별자)

    private float totalScore; // 방금 작성된 충전소의 전체 별점
    private String statNM; // 방금 작성된 충전소의 충전소명



}
