package com.spring.mvc.config;

import com.spring.mvc.interceptor.AutoLoginInterceptor;
import com.spring.mvc.interceptor.BoardInterceptor;
import com.spring.mvc.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//다양한인터셉터에 관련 설정을 등록하는 클래스
@Configuration
@RequiredArgsConstructor //주입받기 위해 필요
public class InterceptorConfig implements WebMvcConfigurer {
    private final BoardInterceptor boardInterceptor;
    private final LoginInterceptor loginInterceptor;
    private final AutoLoginInterceptor autoLoginInterceptor;



//    인터셉터 설정 등록


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        게시판 인터셉터 설정
        registry.addInterceptor(boardInterceptor)
                .addPathPatterns("/board/*") //어떤 경로에서 인터셉터를 실행할 것인가
                .excludePathPatterns("/board/list","/board/detail") //인터셉터를 실행하지 않을 경로
        ;

//        로그인 후처리 인터셉터 설정
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/members/sign-in", "/members/sign-up");
//    자동 로그인 인터셉터 설정
        registry.addInterceptor(autoLoginInterceptor)
                .addPathPatterns("/**")
                ; //어디로 들어올지 모르니 모든 경로 추가 해줌

    }



}
