package com.neighborcharger.capstoneproject.repository;

import com.neighborcharger.capstoneproject.model.chating.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

}
