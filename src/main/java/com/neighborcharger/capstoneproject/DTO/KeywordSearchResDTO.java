package com.neighborcharger.capstoneproject.DTO;

import com.neighborcharger.capstoneproject.model.PrivateStation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordSearchResDTO {

    private List<PrivateStation> correctPrivateStat;

}
