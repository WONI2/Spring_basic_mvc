package com.spring.mvc.chap04.service;


import com.spring.mvc.chap04.DTO.ScoreListResponseDTO;
import com.spring.mvc.chap04.DTO.ScoreRequestDTO;
import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap04.repository.ScoreMapper;
import com.spring.mvc.chap04.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


//컨트롤러와 레파지토리 사이에서 비즈니스로직을 처리
//ex) 트랜잭션처리, 예외처리, dto변환처리

//@RequiredArgsConstructor
@Service //빈등록- 등록해야 사용 할 수 있음
public class ScoreService {

    private final ScoreMapper scoreRepository;

    public ScoreService(ScoreMapper scoreRepository) {

        this.scoreRepository = scoreRepository;
    }

    //    목록조회 중간처리
    /*
    * 컨트롤러는 데이터베이스를 통해 성적정보 리스트 가져오기를 원한다
    * 그런데 데이터베이스는 성적정보를 전부 모아서 준다
    * 컨트롤러는 일부만 받고 싶다.
    *
    *
    * */
   public List<ScoreListResponseDTO> getList(String sort) {

//    scoreList에서 원하는 정보만 추출하고 이름을 마스킹, 다시 DTO로 변환
       return scoreRepository.findAll(sort).stream()
                .map(s ->new ScoreListResponseDTO(s)).collect(Collectors.toList());


   }

//   등록 중간 처리 : 컨트롤러는 dto를 줬지만, 레파지토리는 entity를 달라고 하기 때문에 변환 필요
    public boolean insertScore(ScoreRequestDTO dto) {

        //save 명령
       return  scoreRepository.save(new Score(dto));

    }

//    삭제 중간처리
    public boolean delete(int stuNum) {

       return scoreRepository.deleteByStuNum(stuNum);

    }

//    상세조회, 수정화면 조회 중간처리

    public Score retrieve(int stuNum) {

       return scoreRepository.findByStuNum(stuNum);
    }

}
