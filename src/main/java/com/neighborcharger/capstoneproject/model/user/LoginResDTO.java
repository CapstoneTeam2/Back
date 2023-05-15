package com.neighborcharger.capstoneproject.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResDTO {

    private String resultMessage;

    private Integer userId;
    private String nickname;
    private String carType;
    private String chgerType;
    private String isBusiness;

    private String jwtAccessToken;
    private String jwtRefreshToken;
}
