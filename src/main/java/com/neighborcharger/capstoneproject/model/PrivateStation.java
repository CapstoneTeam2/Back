package com.neighborcharger.capstoneproject.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrivateStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int privateStatKey;

    @Column
    private String statNM; //충전소명

    @Column
    private String ownerName; // 충전소 소유자/대표자명

    @Column
    private String chgerType; // 충전기 타입

    @Column
    private String price; // 1kWh 기준 충전 가격

    @Column
    private String addr; // 주소

    @Column
    private String OpenDate;

    @Column
    private String Business_registration_number;

    @Column
    private String useTime; //사용가능시간

    @Column
    private String reserved;

    @Column
    private String stat; // 충전기 상태

    @Column
    private String statUpdDt; // 상태 갱신 일시

    @Column
    private String nowTsdt; //충전중 시작일시

    @Column
    private String output; // 충전용량

    @Column
    private String method; //충전방식,타입

    @Column
    private String firebaseToken;

    @Column
    private String note;

    @Column
    private String component;

    @Column
    private int totalScore; // 나누기 전 총점

    @Column
    private int reviewCnt;

    @Column
    private float score;

    @Column
    private double totalcost;

    @Column
    private double totalelectric;

    @Column
    @OneToMany
    private List<Reservation_info> reservations = new ArrayList<>();

    @Column
    @OneToMany
    private List<ReviewEntity> reviewList = new ArrayList<>();

}