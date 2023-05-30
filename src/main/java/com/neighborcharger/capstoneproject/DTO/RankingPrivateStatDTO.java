package com.neighborcharger.capstoneproject.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RankingPrivateStatDTO {
    private String statNM; //충전소명
    private String addr; // 주소
    private String score; // 평점
    private String price; // 가격

    @Builder
    public RankingPrivateStatDTO(String statNM, String addr, String score, String price) {
        this.statNM = statNM;
        this.addr = addr;
        this.score = score;
        this.price = price;
    }
}
