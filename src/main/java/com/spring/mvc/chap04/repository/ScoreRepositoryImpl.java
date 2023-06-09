package com.spring.mvc.chap04.repository;

import com.spring.mvc.chap04.DTO.ScoreRequestDTO;
import com.spring.mvc.chap04.entity.Score;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static com.spring.mvc.chap04.entity.Grade.*;
import static java.util.Comparator.*;

//@Component //스프링의 빈 등록: 객체의 생성의 제어권을 스프링에게 위임.
@Repository("memory") // component와 같은 역할. 저장소 빈으로 역할을 함.
public class ScoreRepositoryImpl implements ScoreRepository{

    //key: 학번 , value: 성적정보 // 성적map은 한개, 변경되면 안되고 외부에서 바꿀 수 없게 제한 걸어둘 것
    private static final Map<Integer, Score> scoreMap;

    //학번에 사용할 일련번호
    private static int sequence;

    static {
        scoreMap = new HashMap<>();
        Score stu1 = new Score(new ScoreRequestDTO("포비",100,50,70) );
        Score stu2 = new Score(new ScoreRequestDTO("루피",100,99,5));
        Score stu3 = new Score(new ScoreRequestDTO("에디",70,60,100));

       stu1.setStuNum(++sequence);
       stu2.setStuNum(++sequence);
       stu3.setStuNum(++sequence);

        scoreMap.put(stu1.getStuNum(), stu1);
        scoreMap.put(stu2.getStuNum(), stu2);
        scoreMap.put(stu3.getStuNum(), stu3);

    }

    @Override
    public List<Score> findAll() {
        return new ArrayList<>(scoreMap.values()).stream()
                .sorted(comparing(Score::getStuNum))
                .collect(Collectors.toList());
    }

    @Override
    public List<Score> findAll(String sort) {

        Comparator<Score> comparing = comparing(Score::getStuNum);
        switch (sort) {
            case "stuNum":
                comparing = comparing(Score::getStuNum);
                break;
            case "name" :
                comparing = comparing(Score::getName);
                break;
            case "avg" :
                comparing = comparing(Score::getAverage);
                break;
        }
        return new ArrayList<>(scoreMap.values()).stream()
                .sorted(comparing)
                .collect(Collectors.toList());
    }


    @Override
    public boolean save(Score score) {
        if(scoreMap.containsKey(score.getStuNum())) {
            return false;
        }
        score.setStuNum(++sequence); //map에 들어가기 전에 sequence의 수를 늘려줘서 학번을 지정해줘야 함
        scoreMap.put(score.getStuNum(),score);
        System.out.println(findAll());
        return true;
    }

    @Override
    public boolean deleteByStuNum(int stuNum) {
        if(!scoreMap.containsKey(stuNum)) return false;
        scoreMap.remove(stuNum);
        return true;
    }

    @Override
    public Score findByStuNum(int stuNum) {

        return scoreMap.get(stuNum);
    }


}
