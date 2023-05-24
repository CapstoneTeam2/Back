package com.neighborcharger.capstoneproject.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Entity
@Table(name = "User")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userIdx;

    @Column
    private String nickname;

    @Column(name = "car_type")
    private String carType;

    @Column(name = "chger_type")
    private String chgerType;

    @Column(name = "is_business")
    private String isBusiness;

    @Column
    private String id;

    @Column
    private String password;

//    @Column(name = "kakao_id")
//    private long kakaoIdx;

    @Column(name = "firebase_token")
    private String firebaseToken;


    //일반 로그인 시
    public UserEntity(int userIdx, String nickname,
                      String carType, String chgerType,
                      String isBusiness, String id, String password) {
        this.userIdx = userIdx;
        this.nickname = nickname;
        this.carType = carType;
        this.chgerType = chgerType;
        this.isBusiness = isBusiness;
        this.id = id;
        this.password = password;
    }

    public UserEntity(String nickname, String carType,
                      String chgerType, String id, String password, String firebaseToken) {
        this.nickname = nickname;
        this.carType = carType;
        this.chgerType = chgerType;
        this.id = id;
        this.password = password;
        this.firebaseToken = firebaseToken;
        //this.kakaoIdx = 0;
    }

    //카카오 로그인 시
    public UserEntity(int userIdx, String nickname, String carType,
                      String chgerType, String isBusiness, long kakaoIdx) {
        this.userIdx = userIdx;
        this.nickname = nickname;
        this.carType = carType;
        this.chgerType = chgerType;
        this.isBusiness = isBusiness;
        //this.kakaoIdx = kakaoIdx;
    }

    public UserEntity(String nickname, String carType,
                      String chgerType, long kakaoIdx) {
        this.nickname = nickname;
        this.carType = carType;
        this.chgerType = chgerType;
        //this.kakaoIdx = kakaoIdx;
    }
}


