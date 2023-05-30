package com.neighborcharger.capstoneproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargingCarDTO {
    String runtime;        // 얼마나 충전했는가
    double usingElectric; // 사용한 전기량?
    double Cost; // 비용
}
