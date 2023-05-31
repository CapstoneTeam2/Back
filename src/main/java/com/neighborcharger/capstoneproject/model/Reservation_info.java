package com.neighborcharger.capstoneproject.model;

import com.neighborcharger.capstoneproject.model.user.StationHardWare;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

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

    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    @Column
    private LocalDateTime start_time;

    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    @Column
    private LocalDateTime end_time;

    @OneToOne
    private StationHardWare stationHardWare;

    @Column
    private boolean isReviewed;
}
