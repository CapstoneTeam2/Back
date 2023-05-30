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
    private String reviewerFirebaseToken; // 리뷰 쓴 사람 파베 토큰

    @Column
    private String text; // 리뷰 내용

    @Column
    private String ownerFirebaseToken; // 충전소 소유자 파베토큰

    @CreationTimestamp
    @Column
    private LocalDateTime registerTime;

    public ReviewEntity(int score, String reviewerFirebaseToken, String text, String ownerFirebaseToken) {
        this.score = score;
        this.reviewerFirebaseToken = reviewerFirebaseToken;
        this.text = text;
        this.ownerFirebaseToken = ownerFirebaseToken;
    }
}
