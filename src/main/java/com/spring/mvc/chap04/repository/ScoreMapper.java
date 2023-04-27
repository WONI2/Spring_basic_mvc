package com.spring.mvc.chap04.repository;

import com.spring.mvc.chap04.entity.Score;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScoreMapper {
    List<Score> findAll(String sort);//정렬조회 . default를 붙이면 오버라이딩이 강제가 되지 않음

    //성적 정보 등록
    boolean save(Score score);

    //    성적정보 한 개 삭제
    boolean deleteByStuNum(int stuNum);

    //성적 정보 개별 조회

    Score findByStuNum(int stuNum);

}
