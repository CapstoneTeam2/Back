package com.neighborcharger.capstoneproject.model;

import com.neighborcharger.capstoneproject.model.user.StationHardWare;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation_info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String checking;

    @Column
    private String reservationperson; // 예약자

    @Column
    private String statNM;

    @Column
    private String start_time;

    @Column
    private String end_time;

    @OneToOne
    private StationHardWare stationHardWare;
}
