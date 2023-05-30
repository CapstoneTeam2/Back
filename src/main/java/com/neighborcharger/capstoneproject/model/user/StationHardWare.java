package com.neighborcharger.capstoneproject.model.user;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    String nickname;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column
    LocalDateTime RealStartTime; // 시작된 시간

    @Column
    String RealRunTime; // 진행 시간

    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    @Column
    LocalDateTime RealEndTime; // 끝 시간

    @Column
    String ChgerState; // 충전중 or 충전안되는중

    @Column
    String TargetPercent; // 목표 퍼센트

    @Column
    double cost;

    @Column
    double AmountElectricity; // 전력?충전?량 충전기 타입에 따라 다름 근데 개인은 다 완속..
}