package com.neighborcharger.capstoneproject.repository;

import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationUserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByid(String id);
    Optional<UserEntity> findByfirebaseToken(String token);
    Optional<UserEntity> findBynickname(String nickname);

}

