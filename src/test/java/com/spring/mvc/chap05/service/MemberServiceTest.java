package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.dto.request.LoginRequestDTO;
import com.spring.mvc.chap05.dto.request.SignUpRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Test
    @DisplayName("signupdto 전달하면 회원가입에 성공")
    void joinTest() {
        SignUpRequestDTO dto = new SignUpRequestDTO();
        dto.setAccount("hehehe12");
        dto.setPassword("hihi1234");
        dto.setName("크롱롱");
        dto.setEmail("www2@email.com");
        memberService.join(dto);
    }

//    @Test
//    @DisplayName("계정명이 aaa111인 회원의 로그인 시도시 상황별 결과 검증")
//    void loginTest() {
//        //given
//        LoginRequestDTO dto = new LoginRequestDTO();
//        dto.setAccount("aaa111");
//        dto.setPassword("aaa111!");
//
////        LoginResult result = memberService.authenticate(dto);
//
//        assertEquals(LoginResult.SUCCESS, result);
//
//    }
//

}