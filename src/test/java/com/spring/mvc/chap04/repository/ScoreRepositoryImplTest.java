package com.spring.mvc.chap04.repository;

import com.spring.mvc.chap04.entity.Score;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreRepositoryImplTest {

    ScoreRepository repository =new ScoreRepositoryImpl();

//    단위 테스트(Unit test) : 기능 하나하나의 실행이 잘 되는지 볼 것
//    테스트의 시나리오가 필요.
//    단언(Assertion) 기법
@Test
@DisplayName("저장소에서 findAll을 호출하면 반환된 리스트에는 성적정보가 3개 있어야 한다")
    void findAllTest() {
//    given-when-then 패턴 사용
//    given : 테스트를 위해 주어질 데이터 (ex: parameter)


//    when : 테스트 실제상황
    List<Score> scoreList = repository.findAll();

//    then: 테스트 결과 확인
    System.out.println(scoreList.size() == 3);
    Assertions.assertTrue(scoreList.size() ==3);
    assertEquals(3, scoreList.size());
    // 스코어리스트의 사이즈가 3인 것이 참 -> true면 테스트 성공,

//    리스트의 첫번째 객체의 이름이 포비라고 단언
    assertEquals("포비",scoreList.get(0).getName());

}

@Test
@DisplayName("저장소에서 findByStuNum을 호출하여 학번이 2인 학생일 조회하면 그 학생의 국어점수가 100점이고" +
        " 이름이 루피여야 한다")
void findOneTest() {
    //given
    int stuNum =2;
    //when
    Score score = repository.findByStuNum(stuNum);
    //then
    assertEquals(100, score.getKor());
    assertEquals("루피", score.getName());
}

    @Test
    @DisplayName("저장소에서 findByStuNum을 호출하여 학번이 99인 학생일 조회하면 null이 리턴될 것 ")
    void findOneTest2() {
        //given
        int stuNum =99;
        //when
        Score score = repository.findByStuNum(stuNum);
        //then
       assertNull(score);
    }

@Test
@DisplayName("저장소에서 학번이 2인 학생을 삭제하면 삭제한 후에 " +
        "리스트를 전체조회 해보면 성적의 개수가 2개일 것이고 다시 2번 학생을 조회하면 null이 반환")
void deleteTest() {
//    given
    int stuNum = 2;
//    when
    repository.deleteByStuNum(stuNum);
    List<Score> scoreList = repository.findAll();
    Score score = repository.findByStuNum(stuNum);

//    then
    assertEquals(2, scoreList.size());
    assertNull(score);

    scoreList.forEach(s -> System.out.println(s));

}
@Test
@DisplayName("새로운 성적 정보를 save를 통해 추가하면 목록의 개수가 4개여야 한다")
void saveTest() {
//    given
    Score score = new Score();
    score.setName("크롱");
    score.setKor(50);
    score.setEng(90);
    score.setMath(60);

//    when
    boolean flag = repository.save(score);
    List<Score> scoreList = repository.findAll();

//    then
    assertEquals(4, scoreList.size());
    assertTrue(flag);
    assertEquals(4, scoreList.get(scoreList.size()-1).getStuNum());
    scoreList.forEach(s -> System.out.println(s));

}

}