package com.neighborcharger.capstoneproject.controller;

import com.neighborcharger.capstoneproject.DTO.RegisterReviewReqDTO;
import com.neighborcharger.capstoneproject.DTO.RegisterReviewResDTO;
import com.neighborcharger.capstoneproject.model.PrivateStation;
import com.neighborcharger.capstoneproject.model.ReviewEntity;
import com.neighborcharger.capstoneproject.model.base.BaseException;
import com.neighborcharger.capstoneproject.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/register") //리뷰 등록하는 페이지
    public RegisterReviewResDTO registerReview(@RequestBody RegisterReviewReqDTO registerReviewReqDTO) throws BaseException {
        RegisterReviewResDTO result = reviewService.registerReview(registerReviewReqDTO);
        return result;
    }

    @GetMapping("/perStat/{statNM}") // 충전소당 리뷰 모아보는 페이지
    public List<ReviewEntity> allReviewPerStat(@PathVariable String statNM){
        List<ReviewEntity> result = reviewService.allReviewsPerStation(statNM);
        return result;
    }


    @DeleteMapping("/delete/{reviewerNickname}/{ownerPrivateStatNM}")
    public String deleteReview(@PathVariable String reviewerNickname, @PathVariable String ownerPrivateStatNM){
        reviewService.deleteReview(reviewerNickname, ownerPrivateStatNM);

        return "리뷰를 삭제하였습니다.";
    }

    @GetMapping("/byMe/{nickname}")
    public List<ReviewEntity> myReviewList(@PathVariable String nickname){
        List<ReviewEntity> result = reviewService.myReviewList(nickname);
        return result;
    }





}
