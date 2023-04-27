package com.spring.mvc.chap04.repository;

import com.spring.mvc.chap04.entity.Score;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScoreSpringRepositoryTest {

    ScoreSpringRepository repository;

    @Test
    void saveTest(){
        Score s = new Score();
        s.setName(s.getName());
        s.setKor(s.getKor());
        s.setEng(s.getEng());
        s.setMath(s.getMath());
        s.setTotal(s.getTotal());
        s.setAverage(s.getAverage());
        s.setGrade(s.getGrade());

        repository.save(s);

    }

}