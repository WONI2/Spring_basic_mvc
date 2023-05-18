package com.spring.mvc.chap05.controller;

import com.spring.mvc.chap05.service.SnsLoginService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;


@Controller
@Slf4j
@RequiredArgsConstructor

public class SnsLoginController {

    private final SnsLoginService loginService;
@Value("${sns.kakao.app-key}")
//   카카오 앱 키
    private String kakaoAppKey;
    @Value("${sns.kakao.redirect-uri}")
    // 카카오 redirect uri
    private String kakaoRedirectUri;



    //    카카오 인가코드 발급 요청
    @GetMapping("/kakao/login")
    public String kakaoLogin() {
        String requestUri
                = String.format("https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code",kakaoAppKey,kakaoRedirectUri);
        return "redirect:" +requestUri;
    }

//    인가코드를 받아 토큰 요청
    @GetMapping("/sns/kakao")
    public String snsKakao(String code) {
        log.info("인가코드: {}", code);
// 인가코드를 가지고 카카오서버에 post 요청을 보내야 함. (서버에서 서버로 통신을 하는 방법으로)
        Map<String, String> map = new HashMap<>();
        map.put("appkey", kakaoAppKey);
        map.put("redirect", kakaoRedirectUri);
        map.put("code", code);

        loginService.kakaoService(map);

        return "";
    }

}
