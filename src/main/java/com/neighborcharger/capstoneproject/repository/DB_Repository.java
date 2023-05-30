package com.neighborcharger.capstoneproject.repository;

import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.PublicStation;
import com.neighborcharger.capstoneproject.model.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public interface DB_Repository extends JpaRepository<PublicStation, Integer> {
    List<PublicStation> findAllBychgerType(String chgerType);
    List<PublicStation> findBystatNMContaining(String keyword);


//    @Query("select * from PublicStation group by addr")
//    List<PublicStation> findByDinstinctAddr();
}
