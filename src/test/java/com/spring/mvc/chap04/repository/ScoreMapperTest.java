package com.spring.mvc.chap04.repository;

import com.spring.mvc.chap04.entity.Grade;
import com.spring.mvc.chap04.entity.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScoreMapperTest {

    @Autowired
    ScoreMapper mapper;

    @Test
    @DisplayName("SCORE에 새로 입력 및 저장이 잘 되는지 확인하기")
    void saveTest() {
        Score s = Score.builder()
                .name("포비")
                .kor(100)
                .eng(100)
                .math(100)
                .total(300)
                .average(100)
                .grade(Grade.A)
                .build();
        boolean flag = mapper.save(s);

        assertTrue(flag);

    }
    @Test
    @DisplayName("SCORE에서 삭제가 잘 되는지 확인하기")
    void deleteByStuNumTest() {
        int stuNum = 6;
        boolean flag = mapper.deleteByStuNum(stuNum);
        assertTrue(flag);

    }
    @Test
    @DisplayName("SCORE에서 한 명 찾기 잘 되는지 확인하기")
    void findByStuNumTest() {
        int stuNum = 4;
        Score s = mapper.findByStuNum(stuNum);
        System.out.println("s = " + s);
        assertEquals("콩순이",s.getName());

    }
//    @Test
//    @DisplayName("SCORE에서 전체 리스트 불러오기 ")
//    void findAllTest() {
//        List<Score> score = mapper.findAll();
//        for (Score s : score) {
//            System.out.println("s = " + s);
//        }
//        assertEquals(3, score.size());
//
//
//    }



    }