package com.neighborcharger.capstoneproject.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor

public class StationDTO {

    private boolean isPublicStat; // 공용/개인 충전소 여부

    private String statNM; // 충전소명
    private String addr; // 주소

    @Builder
    public StationDTO(boolean isPublicStat, String statNM, String addr) {
        this.isPublicStat = isPublicStat;
        this.statNM = statNM;
        this.addr = addr;
    }
}
