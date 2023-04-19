package com.spring.mvc.chap03;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/hw/*")
public class LoginController {
/*
        1번요청: 로그인 폼 화면 열어주기
        - 요청 URL : /hw/s-login-form :get
        - 포워딩 jsp파일 경로:  /WEB-INF/views/chap03/s-form.jsp
        - html form: 아이디랑 비번을 입력받으세요.

        2번요청: 로그인 검증하기
        - 로그인 검증: 아이디를 grape111이라고 쓰고 비번을 ggg9999 라고 쓰면 성공
        - 요청 URL : /hw/s-login-check :post
        - 포워딩 jsp파일 경로:  /WEB-INF/views/chap03/s-result.jsp
        - jsp에게 전달할 데이터: 로그인 성공여부, 아이디 없는경우, 비번 틀린경우

     */
    @GetMapping("/s-login-form")
    public String loginForm() {

        System.out.println("로그인 요청 발생");
        return "chap03/s-form";
    }
@PostMapping("/s-login-check")
    public String loginCheck( String id, //받아오는 ID, PWD는 각 태그의 NAME으로 받아오는 것
                          String pwd ,Model model){
    String result = null;
    if(id.equals("grape111") && pwd.equals("ggg9999"))   {
        result = "로그인 성공";
    }else if(id.equals("grape111") && !pwd.equals("ggg9999")) {
        result = "비밀번호가 틀렸습니다";
    }else if (!id.equals("grape111")) {
        result = "아이디가 존재하지 않습니다";
    }
    model.addAttribute("result", result); //마지막에 model에 어떻게 담아서 나가야 하는지 잘 몰랐음!
//                                                          MODEL은 담아서 나가는 역할이니까 result를 받아서 나가야 하고
//
    return "chap03/s-result";
}

}
