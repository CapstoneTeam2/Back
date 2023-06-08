package com.neighborcharger.capstoneproject.service;


import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.Reservation_info;
import com.neighborcharger.capstoneproject.model.base.BaseException;
import com.neighborcharger.capstoneproject.model.user.*;
import com.neighborcharger.capstoneproject.repository.ReservationUserRepository;
import com.neighborcharger.capstoneproject.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.neighborcharger.capstoneproject.model.base.BaseResponseStatus.*;


@Service
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final RedisService redisService;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, RedisService redisService, JwtService jwtService){
        this.userRepository = userRepository;
        this.redisService = redisService;
        this.jwtService = jwtService;
    }

    @Autowired
    ReservationUserRepository reservationUserRepository;

    /*@Transactional
    public CreateUserResDTO createUser(CreateUserReqDTO createUserReqDTO, long kakaoIdx) throws BaseException{

        //닉네임 중복확인
        checkNickname(createUserReqDTO.getNickname());

        //kakao ID 존재여부 확인

        // createUser 메소드에 넘겨줄 UserEntity 객체 생성
        UserEntity userEntity = new UserEntity(
                createUserReqDTO.getNickname(),
                createUserReqDTO.getCarType(),
                createUserReqDTO.getChgerType(),
                kakaoIdx
        );

        int userId = userRepository.createUser(userEntity);

        //JWT
        String jwtAccessToken = jwtService.createAccessToken(userId);
        String jwtRefreshToken = jwtService.createRefreshToken(userId);

        return new CreateUserResDTO(
                true,
                "회원가입에 성공하였습니다.",
                userId,
                createUserReqDTO.getNickname(),
                createUserReqDTO.getCarType(),
                createUserReqDTO.getChgerType(),
                "N",
                jwtAccessToken,
                jwtRefreshToken
        );
    }
*/
//    public LoginResDTO loginUser(long kakaoIdx){
//        UserLoginInfo userLoginInfo = userRepository.getUserLoginInfo(kakaoIdx);
//
//        //JWT
//        String jwtAccessToken = jwtService.createAccessToken(userLoginInfo.getUserId());
//        String jwtRefreshToken = jwtService.createRefreshToken(userLoginInfo.getUserId());
//
//        return new LoginResDTO(
//                "로그인에 성공하였습니다.",
//                userLoginInfo.getUserId(),
//                userLoginInfo.getNickname(),
//                userLoginInfo.getCarType(),
//                userLoginInfo.getChgerType(),
//                userLoginInfo.getIsBusiness(),
//                jwtAccessToken,
//                jwtRefreshToken
//        );
//    }
//
//    //DB에 같은 카카오 ID로 가입되어있는 회원이 있는지 확인
//    public KakaoMemberCheckResDTO checkKakaoMember (long kakaoIdx){
//        //DB에 Kakao ID 존재 여부 확인
//        int isKakaoMemberExisted = 0;
//
//        try{
//            isKakaoMemberExisted = userRepository.checkKakaoMember(kakaoIdx);
//        }catch (Exception e){
//            log.error(e.getMessage());
//        }
//
//        if (isKakaoMemberExisted == 0){
//            return new KakaoMemberCheckResDTO(false, "회원가입을 시작해주세요.");
//        }else{
//            return new KakaoMemberCheckResDTO(false, "이미 회원이니 로그인 해주세요.");
//        }
//    }
//

    // 사용자 생성
    @Transactional
    public CreateUserResDTO createIdUser(CreateIdUserReqDTO createIdUserReqDTO) throws BaseException{

        //지워도 됨
        //닉네임 중복확인
        //checkNickname(createIdUserReqDTO.getNickname());

        // createUser 메소드에 넘겨줄 UserEntity 객체 생성
        UserEntity userEntity = new UserEntity(
                createIdUserReqDTO.getNickname(),
                createIdUserReqDTO.getCarType(),
                createIdUserReqDTO.getChgerType(),
                createIdUserReqDTO.getId(),
                createIdUserReqDTO.getPassword(),
                createIdUserReqDTO.getFirebaseToken()
        );


        int userId = userRepository.createIdUser(userEntity);

        // JWT !!!!!
        String jwtAccessToken = jwtService.createAccessToken(userId);
        String jwtRefreshToken = jwtService.createRefreshToken(userId);

        return new CreateUserResDTO(
                true,
                "회원가입에 성공하였습니다.",
                userId,
                createIdUserReqDTO.getNickname(),
                createIdUserReqDTO.getCarType(),
                createIdUserReqDTO.getChgerType(),
                "N",
                jwtAccessToken,
                jwtRefreshToken,
                createIdUserReqDTO.getFirebaseToken()
        );
    }


    public LoginResDTO loginIdUser (LoginIdReqDTO loginIdReqDTO) throws BaseException {
        UserLoginInfo userLoginInfo = userRepository.getIdUserLoginInfo(loginIdReqDTO.getId(), loginIdReqDTO.getPassword());

        //만약 ID와 PW가 일치하지 않는다면
        if(userLoginInfo == null){
            throw new BaseException(LOGIN_INFO_NOT_MATCH);
        }

        // JWT !!!!!
        String jwtAccessToken = jwtService.createAccessToken(userLoginInfo.getUserId());
        String jwtRefreshToken = jwtService.createRefreshToken(userLoginInfo.getUserId());


        return new LoginResDTO(
                "로그인에 성공하였습니다.",
                userLoginInfo.getUserId(),
                userLoginInfo.getNickname(),
                userLoginInfo.getCarType(),
                userLoginInfo.getChgerType(),
                userLoginInfo.getIsBusiness(),
                jwtAccessToken,
                jwtRefreshToken
        );
    }

    public CheckNicknameResDTO checkNickname(String nickname) throws BaseException{

        int isNicknameExisted = 0;
        try {
            isNicknameExisted = userRepository.checkNickname(nickname);
        } catch (Exception e) {
            log.error("DATABASE_ERROR when call UserRepository.checkNickname()");
        }

        if (isNicknameExisted == 0) {
            return new CheckNicknameResDTO(false, "사용 가능한 닉네임입니다.");
        } else {
            log.error("ILLEGAL_ARG_ERROR when call UserRepository.checkNickname() because nickname is already used");
            throw new BaseException(SIGNUP_ALREADY_EXIST_NICKNAME);
        }
    }

    public CheckNicknameResDTO checkId(String id) throws BaseException{
        int isIdExisted = 0;
        try {
            isIdExisted = userRepository.checkId(id);
        } catch (Exception e) {
            log.error("DATABASE_ERROR when call UserRepository.checkNickname()");
        }

        if (isIdExisted == 0) {
            return new CheckNicknameResDTO(false, "사용 가능한 아이디입니다.");
        } else {
            log.error("ILLEGAL_ARG_ERROR when call UserRepository.checkNickname() because nickname is already used");
            throw new BaseException(SIGNUP_ALREADY_EXIST_ID);

        }
    }

    public CheckNicknameResDTO checkAlreadyUser(String nickname) throws BaseException{

        int isNicknameExisted = 0;
        try {
            isNicknameExisted = userRepository.checkNickname(nickname);
        } catch (Exception e) {
            log.error("DATABASE_ERROR when call UserRepository.checkNickname()");
        }

        if (isNicknameExisted == 0) {
            return new CheckNicknameResDTO(false, "사용 가능한 닉네임입니다.");
        } else {
            log.error("ILLEGAL_ARG_ERROR when call UserRepository.checkNickname() because nickname is already used");
            throw new BaseException(SIGNUP_ALREADY_EXIST_NICKNAME);

        }
    }
    public UserEntity User_get(String id){
        UserEntity userEntity = reservationUserRepository.findByid(id).orElseGet(UserEntity::new);
        return userEntity;
    }

    public UserEntity User_get_usingname(String name){
        UserEntity userEntity = reservationUserRepository.findBynickname(name).orElseGet(UserEntity::new);
        return userEntity;
    }

    public List<Reservation_info> getReservation(String id){
        UserEntity userEntity = reservationUserRepository.findByid(id).orElseGet(UserEntity::new);
        List<Reservation_info> reservationinfoList = new ArrayList<>();

        for (Reservation_info reservation_info : userEntity.getReservations()){
            if(!reservation_info.getChecking().equals("거절")){
                reservationinfoList.add(reservation_info);
            }
        }
        return reservationinfoList;
    }

    @Transactional
    public void ReservationUpdate(String name, String StationName,String rep){
        UserEntity userEntity = reservationUserRepository.findBynickname(name).orElseGet(UserEntity::new);
        for(Reservation_info reservation_info : userEntity.getReservations()){
            if(reservation_info.getStatNM().equals(StationName))
                reservation_info.setChecking(rep);
        }
    }

    @Transactional
    public void insertReservation(String name, Reservation_info reservation_info){
        UserEntity userEntity = reservationUserRepository.findBynickname(name).orElseGet(UserEntity::new);
        userEntity.getReservations().add(reservation_info);
    }

    @Transactional
    public void deleteUser(String nickname){
        UserEntity user = reservationUserRepository.findBynickname(nickname).orElseGet(UserEntity::new);
        int userIdx = user.getUserIdx();

        reservationUserRepository.deleteById(userIdx);
    }

    @Transactional
    public void allDeleteUser(){
        reservationUserRepository.deleteAll();
    }

}
