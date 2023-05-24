package com.neighborcharger.capstoneproject.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResDTO {
    private Boolean isCreated;
    private String resultMessage;

    private int userId;
    private String nickname;
    private String carType;
    private String chgerType;
    private String isBusiness;

    private String jwtAccessToken;
    private String jwtRefreshToken;
    private String firebaseToken;

}
