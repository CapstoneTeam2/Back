package com.neighborcharger.capstoneproject.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrivateStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long privateStatKey;

    @Column(length = 20)
    private String statNM; //충전소명

    @Column(length = 10)
    private String ownerName; // 충전소 소유자명

    @Column(length = 2)
    private String chgerType; // 충전기 타입

    @Column(length = 10)
    private double price; // 1kWh 기준 충전 가격

    @Column(length = 30)
    private String addr; // 주소

    @Column(length = 10)
    private String lat; //위도

    @Column(length = 10)
    private String lng; //경도

    @Column(length = 30)
    private String useTime; //사용가능시간

    @Column(length = 5)
    private boolean reserved;

    @Column(length = 1)
    private String stat; // 충전기 상태

    @Column(length = 14)
    private String statUpdDt; // 상태 갱신 일시

    @Column(length = 14)
    private String nowTsdt; //충전중 시작일시

    @Column(length = 3)
    private String output; // 충전용량

    @Column(length = 2)
    private String method; //충전방식

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;
}