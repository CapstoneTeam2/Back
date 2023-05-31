package com.neighborcharger.capstoneproject.service;

import com.neighborcharger.capstoneproject.DTO.RegisterReviewReqDTO;
import com.neighborcharger.capstoneproject.DTO.RegisterReviewResDTO;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.Reservation_info;
import com.neighborcharger.capstoneproject.model.ReviewEntity;
import com.neighborcharger.capstoneproject.model.base.BaseException;
import com.neighborcharger.capstoneproject.model.user.CreateIdUserReqDTO;
import com.neighborcharger.capstoneproject.model.user.CreateUserResDTO;
import com.neighborcharger.capstoneproject.model.user.UserEntity;
import com.neighborcharger.capstoneproject.repository.DB_Repository_private;
import com.neighborcharger.capstoneproject.repository.ReservationUserRepository;
import com.neighborcharger.capstoneproject.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private DB_Repository_private db_repository_private;

    @Autowired
    private ReservationUserRepository reservationUserRepository;

    @Transactional
    public RegisterReviewResDTO registerReview(RegisterReviewReqDTO registerReviewReqDTO) throws BaseException {

        ReviewEntity reviewEntity = new ReviewEntity( //리뷰 엔티티 등록
                registerReviewReqDTO.getScore(),
                registerReviewReqDTO.getReviewerNickname(),
                registerReviewReqDTO.getText(),
                registerReviewReqDTO.getOwnerPrivateStatNM()
        );

        reviewRepository.save(reviewEntity);

        //리뷰한 사람 닉네임으로 정보 찾아내기
        UserEntity reviewer = reservationUserRepository.findBynickname(registerReviewReqDTO.getReviewerNickname()).orElseGet(()->{
            return new UserEntity();
        });

        //해당 개인 충전소의 totalScore 변경하기
            //먼저 해당 개인 충전소 정보 가져오기
        PrivateStation selectedPrivateStat = db_repository_private.findBystatNM(registerReviewReqDTO.getOwnerPrivateStatNM()).orElseGet(
                () -> {return new PrivateStation();});

        //충전소의 리뷰 리스트에 이번 리뷰 더하기
        selectedPrivateStat.getReviewList().add(reviewEntity);
        //해당 개인이 쓴 리뷰 리스트에 이번 리뷰 더하기
        reviewer.getReviewList().add(reviewEntity);

        //리뷰한 사람 찾아낸 걸로 해당 충전소 리뷰쓴 boolean 값 바꾸기
        for(Reservation_info reservation_info : reviewer.getReservations()){
            if (reservation_info.getStatNM() == selectedPrivateStat.getStatNM()){
                reservation_info.setReviewed(true);
            }
        }

            // 총 별점이랑 리뷰 수 가져오기
        int totalScore;
        int reviewTotalCnt;
        if (selectedPrivateStat.getReviewCnt() != 0){
            totalScore = selectedPrivateStat.getTotalScore() + registerReviewReqDTO.getScore();
            reviewTotalCnt = selectedPrivateStat.getReviewCnt() + 1;
        }else{ // 리뷰 첫번째 사람이면
            totalScore = registerReviewReqDTO.getScore();
            reviewTotalCnt = 1;
        }

        // 총 별점과 리뷰수 업데이트
        selectedPrivateStat.setTotalScore(totalScore);
        selectedPrivateStat.setReviewCnt(reviewTotalCnt);

        // 별점 업데이트
        float resultScore = totalScore/reviewTotalCnt;
        selectedPrivateStat.setScore(resultScore);


        return new RegisterReviewResDTO(
                "리뷰가 등록되었습니다.",
                reviewer.getNickname(),
                registerReviewReqDTO.getScore(),
                registerReviewReqDTO.getText(),
                reviewEntity.getReviewIdx(),
                resultScore,
                selectedPrivateStat.getStatNM()
        );

    }

    @Transactional
    public List<ReviewEntity> allReviewsPerStation(String name) { // 하나의 충전소마다 리뷰 모아보기
        PrivateStation findPrivateStat = db_repository_private.findBystatNM(name).orElseGet(
                () -> {
                    return new PrivateStation();
                });;

        List<ReviewEntity> allReviewsPerStat = findPrivateStat.getReviewList();

        return allReviewsPerStat;
    }

    @Transactional
    public List<ReviewEntity> myReviewList(String nickname){
        UserEntity userIsMe = reservationUserRepository.findBynickname(nickname).orElseGet(()->{
            return new UserEntity();
        });

        return userIsMe.getReviewList();
    }

    @Transactional
    public void deleteReview(String reviewerNickname, String ownerPrivateStatNM) {   // 리뷰 삭제

        ReviewEntity selectedReview = reviewRepository.findByreviewerNickname(reviewerNickname)
                .orElseGet(()-> {return new ReviewEntity();});

        int selectedReviewIdx = selectedReview.getReviewIdx();

        //사용자의 리뷰 리스트에서도 지우기
        UserEntity reviewer = reservationUserRepository.findByid(reviewerNickname).orElseGet(()->{
            return new UserEntity();
        });
        reviewer.getReviewList().remove(selectedReview);

        //개인 충전소 리뷰 내역에서도 지우기
        PrivateStation selectedPrivateStat = db_repository_private.findBystatNM(ownerPrivateStatNM).orElseGet(
                () -> {return new PrivateStation();});
        selectedPrivateStat.getReviewList().remove(selectedReview);

        //마지막으로 reviewEntity에서도 지우기
        reviewRepository.deleteById(selectedReviewIdx);
    }

}
