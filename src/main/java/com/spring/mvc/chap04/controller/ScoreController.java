
package com.spring.mvc.chap04.controller;

import com.spring.mvc.chap04.DTO.ScoreListResponseDTO;
import com.spring.mvc.chap04.DTO.ScoreRequestDTO;
import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap04.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/*
* 요청 URL
* 1. 학생 성적정보 등록화면을 보여주고 성적정보 목록조회 처리
* - /score/list : GET
*
* 2. 성적정보 등록 처리 요청
* - /score/register : POST
*
* 3. 성적정보 삭제 요청
* - /score/remove : POST
*
* 4. 성적정보 상세 조회 요청
* - /score/detail : GET
*
* */


@Controller
@RequestMapping("/score")
//@AllArgsConstructor //모든 필드의 초기화
//all~로 받아서 생성자를 따로 설정할 필요가 없어짐.
@RequiredArgsConstructor // 파이널 필드만 초기화하는 생성자를 만듦.
// 저장소가 final로 불변성을 가지고 있기 때문에 주입대상에 대하여 주입객체가 여러개여도 단 한 개의 자동주입을 받아올 수 있다.
public class ScoreController {



//final로 객체 불변성 유지 가능 ,
private final ScoreService scoreService;
//    빈등록 개념.()
//    @Autowired
//    // 저장소를 바로 주입해주기 위하여 사용. autowired 생략해도 가능한 이유는
////    만약에 클래스의 생성자가 한 개라면 자동으로 autowired를 써줌. 생성자가 여러개라면
////    주입받을 생성자를 선택해서 달아줘야 함.
//    public ScoreController(ScoreRepository repository) {
//        this.repository = repository;
//    }

    //1번 요청 : 학생 성적정보 등록화면을 보여주고 성적정보 목록조회 처리
@GetMapping("/list")
public String list(Model model, @RequestParam(defaultValue = "stuNum") String sort) {
    System.out.println("/score/list : GET");
    System.out.println("정렬요구사항 " + sort);


// service 만들기 전 방법
//    List<Score> scoreList = repository.findAll(sort);
////    scoreList에서 원하는 정보만 추출하고 이름을 마스킹, 다시 DTO로 변환
//    List<ScoreListResponseDTO> dto = scoreList.stream()
//            .map(s ->new ScoreListResponseDTO(s)).collect(Collectors.toList());

//    service 만든 후 방법
    List<ScoreListResponseDTO> dto = scoreService.getList(sort); //dto로 받지 않고 바로 model에 넣어 줘도 됨
    model.addAttribute("sList",dto);
    System.out.println("dto = " + dto);
    return "chap04/scoreList";
}


//2번 요청:성적정보 등록 처리
@PostMapping("/register")
public String register(ScoreRequestDTO dto) {
    //    입력데이터(쿼리스트링) 읽기
    System.out.println("/score/register : POST");
//    dto(ScoreDTO)를 entity(Score)로 변환해야함
//    Score score = new Score(dto);
//    //save 명령
//    repository.save(score);
    scoreService.insertScore(dto);
/*
* 등록요청에서 jsp 뷰 포워딩을 하면 갱신된 목록을 다시한번 저장소에서 불러와
* 모델에 담는 추가적인 코드가 필요하지만
*
* 리다이렉트를 통해서 위에서 만든 /score/list : get을 자동으로 다시 보낼 수 있다면
* 번거로운 코드를 줄어들도록 할 수 있다.
* */
    return "redirect:/score/list"; //요청 url을 작성해야 함 .jsp 파일을 작성하는 것이 아님!
}
//3번 요청:성적정보 삭제
    @GetMapping("/remove")
    public String remove(@RequestParam int stuNum) {
        System.out.println("/score/remove : GET");

        scoreService.delete(stuNum);
        return "redirect:/score/list";
    }

    //4번 요청
@GetMapping("/detail")
public String detail(@RequestParam  int stuNum, Model model){
    System.out.println("/score/detail : GET");
    retrieve(stuNum, model);
    return "chap04/score-detail";
}
@GetMapping("/modify")
//get과 post가 다르면 같은 주소값을 써도 다르게 받을 수 있음
public String modify(int stuNum, Model model){

    retrieve(stuNum, model);

    return "chap04/score-modify";
}
//수정완료처리
@PostMapping("/modify")
public String modify(int stuNum, ScoreRequestDTO dto) {
    Score s = scoreService.retrieve(stuNum);
    s.setDto(dto);
//    model.addAttribute("score", s);

    return "redirect:/score/detail?stuNum="+stuNum;
}

    private void retrieve(int stuNum, Model model) {
        Score score = scoreService.retrieve(stuNum);
        model.addAttribute("score", score);
    }



}
