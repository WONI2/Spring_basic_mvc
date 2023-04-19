package com.spring.mvc.chap02;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

//응답 데이터 처리
@RequestMapping("/model/*")
@Controller
public class ControllerV2 {
//    /model/hobbies : get
//    jsp 파일로 이름정보와 취미목록 정보를 보내고 싶을 때

//    1. Model 객체 활용
    @GetMapping("/hobbies")
    public String hobbies(Model model) {

        String name = "멍멍이";
        List<String> hobbies = List.of("풀뜯기", "산책하기", "터그놀이");

//        jsp로 보낼 데이터를 모델에 담는다
        model.addAttribute("key", name);
        model.addAttribute("hList", hobbies);

//        /WEB-INF/views/chap02/hobbies.jsp
        return "chap02/hobbies";
    }

//    2.ModelAndView 사용하기
//    /model/hobbies2 : get
//    hobbies.jsp 포워딩
//    ModelAndView는 리턴타입을 해당 것으로 지정
    @GetMapping("/hobbies2")
    public ModelAndView hobbies2() {

//        jsp로 보낼 데이터
        String name = "짹짹이";
        List<String> hList = List.of("전깃줄에서 졸기");

//        포워딩할 뷰의 이름
        String viewName = "chap02/hobbies";
//        ModelAndView 객체에 위 데이터 담기
        ModelAndView mv = new ModelAndView();
        mv.setViewName(viewName);
        mv.addObject("key", name);
        mv.addObject("hList", hList);


        return mv;
    }

}
