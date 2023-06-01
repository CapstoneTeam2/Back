package com.neighborcharger.capstoneproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PredictReqDTO {
    int wantpercent;
    String carmodelname;
    int stationcost;
    String power;
}
