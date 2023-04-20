
package com.spring.mvc.chap04.controller;

import com.spring.mvc.chap04.DTO.ScoreRequestDTO;
import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap04.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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


  private final ScoreRepository repository;
//final로 객체 불변성 유지 가능 ,

//    빈등록 개념.
//    @Autowired
//    // 저장소를 바로 주입해주기 위하여 사용. autowired 생략해도 가능한 이유는
////    만약에 클래스의 생성자가 한 개라면 자동으로 autowired를 써줌. 생성자가 여러개라면
////    주입받을 생성자를 선택해서 달아줘야 함.
//    public ScoreController(ScoreRepository repository) {
//        this.repository = repository;
//    }

    //1번 요청 : 학생 성적정보 등록화면을 보여주고 성적정보 목록조회 처리
@GetMapping("/list")
public String list(Model model) {
    System.out.println("/score/list : GET");

    List<Score> scoreList = repository.findAll();
    model.addAttribute("sList",scoreList);

    return "chap04/scoreList";
}


//2번 요청:성적정보 등록 처리
@PostMapping("/register")
public String register(ScoreRequestDTO dto) {
    //    입력데이터(쿼리스트링) 읽기
    System.out.println("/score/register : POST");
//    dto(ScoreDTO)를 entity(Score)로 변환해야함
    Score score = new Score(dto);
    //save 명령
    repository.save(score);

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

        repository.deleteByStuNum(stuNum);
        return "redirect:/score/list";
    }

    //4번 요청
@GetMapping("/detail")
    public String detail() {
    System.out.println("/score/detail : GET");
    return "";
}




}
