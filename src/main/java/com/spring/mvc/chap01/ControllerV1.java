package com.spring.mvc.chap01;


//어떤 요청들을 처리할지 공통 url을 설계

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/spring/*")
//이 클래스의 객체를 스프링이 관리하도록 빈을 등록
@Controller //@Component를 포함한 같은 개념, controllerMapping 기능을 알기 쉽게 추가 되어 있음.
public class ControllerV1 {
    //세부요청들은 메서드를 통해 처리

    @RequestMapping("/hello") // http://localhost:8181/spring/hello
    public String hello() {
        System.out.println("\n 헬로 요청이 들어옴! \n");
        return "hello"; //어떤 jsp를 열어줄지 경로를 return에 적기.
    }

    // /spring/food 요청이오면 food.jsp를 열어보세요

    @RequestMapping("/food")
    public String food() {

//        return "/WEB-INF/views/chap01/food.jsp"
        return "chap01/food";
    }

    // 요청 파라미터 읽기 (Query String Parameter)
    //1. HttpServletRequest 사용하기
    // ex) /spring/person?name=kim&age=30
    @RequestMapping("/person")
    public String person(HttpServletRequest request) {
        String name = request.getParameter("name");
        String age = request.getParameter("age");

        System.out.println("name = " + name);
        System.out.println("age = " + age);

        return "";
    }
//2. @RequestParam 사용하기
//ex) /spring/major?stu=kim&major=business&grade=3

 @ RequestMapping("/major")
    public String major(  @RequestParam String stu,// 만약  parameter의 key값 = 변수명 이면 @RequestParam 생략가능
                          @RequestParam("major") String mj, // parameter의 key값과 변수명이 다를 경우 ("")안에 작성.
                          @RequestParam(defaultValue = "1") int grade) { //defaultValue 값이 전달되어오지 않았을 때 기본값 설정
     System.out.println("stu = " + stu);
     System.out.println("major = " + mj);
     System.out.println("grade = " + grade);

        return "";
    }

//3. 커맨드 객체 이용하기
//    쿼리 스트링의 양이 너무 많거나 연관성이 있는 경우 사용
//    ex) /spring/order?oNum=20230419007-P&goods=고구마&amount=3&price=30000

@RequestMapping("/order")
    public String order(OrderRequestDTO dto) {
    System.out.println("dto = " + dto);
    return "";
}
// 4. URL에 경로로 붙어있는 데이터 읽기
//    /spring/member/hong/107
//    hong이라는 유저의 107번 게시글을 읽고 싶음

    @RequestMapping("/member/{userName}/{bNo}")
    public String member(
            @PathVariable String userName,//@PathVariable는 ? 뒤에 있는 것이 아니라 / 뒤를 읽는 것({userName})
            @PathVariable long bNo //@PathVariable 생략불가
    ) {
        System.out.println("userName = " + userName);
        System.out.println("bNo = " + bNo);

        return "";
    }

//음식선택 요청처리
//    @RequestMapping(value = "/food-select", method = RequestMethod.POST)
    @PostMapping("/food-select")
public String foodSelect(
        String foodName,
        String category
    ) {
        System.out.println("foodName = " + foodName);
        System.out.println("category = " + category);

        return "";
    }



}
