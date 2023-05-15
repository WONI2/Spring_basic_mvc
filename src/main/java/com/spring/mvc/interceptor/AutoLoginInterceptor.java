package com.spring.mvc.interceptor;

import com.spring.mvc.chap05.entity.Member;
import com.spring.mvc.chap05.repository.MemberMapper;
import com.spring.mvc.chap05.service.MemberService;
import com.spring.mvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class AutoLoginInterceptor implements HandlerInterceptor {
   private final MemberMapper memberMapper;
   private final MemberService memberService;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        1. 자동 로그인 쿠키를 탐색
//        Cookie[] cookies = request.getCookies(); //배열로 오기 때문에 원래는 반복문으로 찾아봐야.
        Cookie cookie = WebUtils.getCookie(request, LoginUtil.AUTO_LOGIN_COOKIE);
        if(cookie != null) {
//            2. 쿠키에 저장된 세션 아이디를 읽음
            String sessionId = cookie.getValue();
//            3. DB에서 세션 아이디로 회원정보 조회
            Member foundMember = memberMapper.findMemberByCookie(sessionId);
//            4. 회원조회가 되었고 자동로그인 만료일 이전이라면
            if(foundMember != null && LocalDateTime.now().isBefore(foundMember.getLimitTime())) {
//                5. 로그인 처리
                memberService.maintainLoginState(request.getSession(), foundMember.getAccount());
            }
        }
            return true;

    }
}
