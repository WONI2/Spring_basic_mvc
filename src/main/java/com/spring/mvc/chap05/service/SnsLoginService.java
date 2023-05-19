package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.dto.request.SignUpRequestDTO;
import com.spring.mvc.chap05.dto.sns.KakaoUserDTO;
import com.spring.mvc.chap05.entity.LoginMethod;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SnsLoginService {

    //우리 사이트 회원가입할 때 필요한 정보 불러오기
    private final MemberService memberService;

    //    카카오 로그인 처리
    public void kakaoService(Map<String, String> requestMap, HttpSession session) {
//        인가코드를 통해 토큰 발급 받기
        String accessToken = getKakaoAccessToken(requestMap);
        log.info("accessToken : {} ", accessToken);
//       토큰을 통해 사용자 정보 가져오기
        KakaoUserDTO dto = getKakaoUserInfo(accessToken);

//        아이디 이메일 중복확인 검사
        KakaoUserDTO.KakaoAccount kakaoAccount = dto.getKakaoAccount();
        if (!memberService.checkSignUpValue("account", kakaoAccount.getEmail())
                && !memberService.checkSignUpValue("email", kakaoAccount.getEmail())) {
//            사용자 정보를 통해 우리 서비스 회원가입 진행

            memberService.join(
                    SignUpRequestDTO.builder()
                            .account(kakaoAccount.getEmail())
                            .email(kakaoAccount.getEmail())
                            .name(kakaoAccount.getProfile().getNickname())
                            .password("9999")
                            .LoginMethod(LoginMethod.SNS)
                            .build(),
                    kakaoAccount.getProfile().getProfileImageUrl()
            );
        }
//         우리 서비스에서의 로그인 처리
        memberService.maintainLoginState(session, kakaoAccount.getEmail());


    }

    private KakaoUserDTO getKakaoUserInfo(String accessToken) {
        String requestUri = "https://kapi.kakao.com/v2/user/me";

//        요청헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

//        요청 보내기
        RestTemplate template = new RestTemplate();
        ResponseEntity<KakaoUserDTO> responseEntity = template.exchange(
                requestUri,
                HttpMethod.GET,
                new HttpEntity<>(headers),
//                Map.class // dto로 받는 걸로 바꿨기 때문에 <> 내부도 dto로 바꾸기
                KakaoUserDTO.class
        );

//        응답 바디 읽기
//        Map<String, Object> responseData = responseEntity.getBody();
        KakaoUserDTO responseData = responseEntity.getBody();
        log.info("user profile : {}", responseData);

        return responseData;
    }

    private String getKakaoAccessToken(Map<String, String> requestMap) {


        //    요청 uri
        String requestUri = "https://kauth.kakao.com/oauth/token";

        //    요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//요청 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", requestMap.get("appkey"));
        params.add("redirect_uri", requestMap.get("redirect"));
        params.add("code", requestMap.get("code"));


//        카카오 서버로 post통신

        RestTemplate template = new RestTemplate(); //서버에서 서버로 통신할 때 씀

        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);
//exchange: 통신을 보내면서 응답데이터를 리턴
//        params: 1. 요청 url, 2. 요청 메서드
//        3. 헤터와 요청파라미터 정보 엔터티
//        4. 응답데이터를 받을 객체의 타입(ex. dto, map 등) :json으로 넘어올 정보를 받을 객체를 받아 놓을 것.
//        map으로 받으면 그대로 정보를 저장해줌
        ResponseEntity<Map> responseEntity = template.exchange(requestUri, HttpMethod.POST, requestEntity, Map.class);

//        응답데이터에서 필요한 정보 가져오기 (body : responseData)
        Map<String, Object> body = (Map<String, Object>) responseEntity.getBody();
        log.info("토큰 요청 응답데이터 : {} ", body);

        return (String) body.get("access_token");


    }

}
