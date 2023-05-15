package com.neighborcharger.capstoneproject.repository;


import com.neighborcharger.capstoneproject.model.user.UserEntity;
import com.neighborcharger.capstoneproject.model.user.UserLoginInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Log4j2
public class UserRepository {

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private JdbcTemplate jdbcTemplate;

    public int createUser(UserEntity userEntity){
        //User 테이블에 데이터 추가
        String createUserQuery = "insert into User(nickname, car_type, chger_type, kakao_id) values(?,?,?,?)";
        Object[] createUserParams = new Object[]{
                userEntity.getNickname(),
                userEntity.getCarType(),
                userEntity.getChgerType(),
                userEntity.getKakaoIdx()
        };
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        //userIdx 반환
        String lastInsertUserIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertUserIdxQuery, int.class);
    }

    public int createIdUser(UserEntity userEntity){
        //User 테이블에 데이터 추가
        String createUserQuery = "insert into User(nickname, car_type, chger_type, id, password) values(?,?,?,?,?)";
        Object[] createUserParams = new Object[]{
                userEntity.getNickname(),
                userEntity.getCarType(),
                userEntity.getChgerType(),
                userEntity.getId(),
                userEntity.getPassword(),
        };
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        //userIdx 반환
        String lastInsertUserIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertUserIdxQuery, int.class);
    }

    public int checkNickname(String nickname) {
        String checkNicknameQuery = "select exists(select nickname from User where nickname=?)";
        String checkNicknameParam = nickname;
        return this.jdbcTemplate.queryForObject(
                checkNicknameQuery,
                int.class,
                checkNicknameParam);
    }

    public int checkId(String id) {
        String checkIdQuery = "select exists(select id from User where id=?)";
        String checkIdParam = id;
        return this.jdbcTemplate.queryForObject(
                checkIdQuery,
                int.class,
                checkIdParam);
    }

    public String checkIsBusiness(String id) {
        String checkIdQuery = "select is_business from User where id=?";
        String checkIdParam = id;
        return this.jdbcTemplate.queryForObject(checkIdQuery,
                String.class,
                checkIdParam);
    }

    public int checkKakaoMember(long kakaoIdx) {
        String checkKakaoMemberQuery = "select exists(select kakao_id from User where kakao_id=?)";
        long checkKakaoMemberParam = kakaoIdx;
        return this.jdbcTemplate.queryForObject(
                checkKakaoMemberQuery,
                int.class,
                checkKakaoMemberParam);
    }

    public UserLoginInfo getUserLoginInfo(long kakaoIdx) {
        String userLoginInfoQuery =
                "select u.user_id, u.nickname, u.car_type, u.chger_type, u.is_business\n" +
                        "from User u \n" +
                        "where u.kakao_id = ?";

        try {
            return this.jdbcTemplate.queryForObject(userLoginInfoQuery,
                    (rs, row) -> new UserLoginInfo(
                            rs.getInt("user_id"),
                            rs.getString("nickname"),
                            rs.getString("car_type"),
                            rs.getString("chger_type"),
                            rs.getString("is_business")
                    ),
                    kakaoIdx);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public UserLoginInfo getIdUserLoginInfo(String id, String password) {
        String userLoginInfoQuery =
                "select u.user_id, u.nickname, u.car_type, u.chger_type, u.is_business\n" +
                        "from User u \n" +
                        "where u.id = ? and u.password = ?";

        try {
            return this.jdbcTemplate.queryForObject(userLoginInfoQuery,
                    (rs, row) -> new UserLoginInfo(
                            rs.getInt("user_id"),
                            rs.getString("nickname"),
                            rs.getString("car_type"),
                            rs.getString("chger_type"),
                            rs.getString("is_business")
                    ),
                    id, password);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public UserEntity selectByUserId(int userIdx) {
        List<UserEntity> result = jdbcTemplate.query("select * from User where user_id = ?",
                (rs, row) -> new UserEntity(
                        rs.getInt("user_id"),
                        rs.getString("nickname"),
                        rs.getString("car_type"),
                        rs.getString("chger_type"),
                        rs.getString("is_business"),
                        rs.getLong("kakao_id")
                ),
                userIdx
        );
        return result.get(0);
    }

    // User가 개인사업자로 등록할 경우
    public void registerBusiness(int userIndex) {
        String userRegisterBusinessQuery = "update User set is_business = ? where user_id = ?";
        Object[] params = new Object[]{
                "Y", userIndex
        };

        this.jdbcTemplate.update(userRegisterBusinessQuery, params);
    }

}
