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
public class PublicStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int publicStatKey;

    @Column
    private String statNM; //충전소명

    @Column
    private String chgerId; //충전기 ID

    @Column
    private String chgerType; // 충전기 타입

    @Column
    private String addr; // 주소

    @Column
    private String useTime; //사용가능시간

    @Column(length = 10)
    private String bnm; // 기관명

    @Column(length = 10)
    private String stat; // 충전기 상태

    @Column(length = 14)
    private String statUpdDt; // 상태 갱신 일시

//    @Column(length = 14)
//    private String lastTsdt; // 마지막 충전시작일시
//
//    @Column(length = 14)
//    private String lastTedt; //마지막 충전종료일시

    @Column(length = 14)
    private String nowTsdt; //충전중 시작일시

    @Column(length = 30)
    private String output; // 충전용량

    @Column(length = 20)
    private String method; //충전방식

    @Column(length = 10)
    private String parkingFree; // 주차료무료

    @Column(length = 200)
    private String note; //충전소 안내사항
}