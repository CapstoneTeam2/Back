package com.neighborcharger.capstoneproject.repository;

import com.neighborcharger.capstoneproject.model.ReviewEntity;
import com.neighborcharger.capstoneproject.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    //Optional<ReviewEntity> findByprivateStationId(int statId);

    Optional<ReviewEntity> findByreviewerNickname(String reviewerNickname);
}
