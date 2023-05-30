package com.neighborcharger.capstoneproject.model;

import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_idx")
    private int reviewIdx; // 리뷰인덱스

    @Column
    private int score; // 별점

    @Column
    private String reviewerNickname; // 리뷰 쓴 사람 닉네임

    @Column
    private String text; // 리뷰 내용

    @Column
    private String ownerPrivateStatNM; // 충전소명

    @CreationTimestamp
    @Column
    private LocalDateTime registerTime;

    public ReviewEntity(int score, String reviewerNickname, String text, String ownerPrivateStatNM) {
        this.score = score;
        this.reviewerNickname = reviewerNickname;
        this.text = text;
        this.ownerPrivateStatNM = ownerPrivateStatNM;
    }
}
