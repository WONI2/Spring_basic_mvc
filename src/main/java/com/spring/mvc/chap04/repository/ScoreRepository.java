package com.spring.mvc.chap04.repository;

//역할 명세 (추상화):
//성적 정보를 잘 저장하고 조회하고 삭제하고 수정. 어디에서 하는지는 인터페이스에서 알 수 없음

import com.spring.mvc.chap04.entity.Score;

import java.util.List;

public interface ScoreRepository {

    //성적 정보 전체 목록 조회
    List<Score> findAll();

    //성적 정보 등록
    boolean save(Score score);

//    성적정보 한 개 삭제
    boolean deleteByStuNum(int stuNum);

    //성적 정보 개별 조회

    Score findByStuNum(int stuNum);



}
