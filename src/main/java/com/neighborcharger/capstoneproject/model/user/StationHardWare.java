package com.neighborcharger.capstoneproject.model.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationHardWare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    String statNM;

    @Column
    String RealStartTime; // 시작된 시간

    @Column
    String RealRunTime; // 진행 시간

    @Column
    String RealEndTime; // 끝 시간

    @Column
    String ChgerState; // 충전중 or 충전안되는중

    @Column
    String TargetPercent; // 목표 퍼센트
}