package com.neighborcharger.capstoneproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Respone_DTO {
    String token;
    String Name; // 예약자이름
    String Station_name; // 충전소이름
    String check;
    int choice;
}
