package com.neighborcharger.capstoneproject.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginInfo {

    private int userId;
    private String nickname;
    private String carType;
    private String chgerType;
    private String isBusiness;

}
