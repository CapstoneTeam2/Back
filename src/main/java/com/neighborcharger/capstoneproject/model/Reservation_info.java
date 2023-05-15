package com.neighborcharger.capstoneproject.model;

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
    private Long id;
    @Column
    private String reservation_person; // 예약자
    @Column
    private String statNM;
    @Column
    private String start_time;
    @Column
    private String end_time;
}
