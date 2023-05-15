package com.spring.mvc.interceptor;

import com.spring.mvc.chap05.entity.Board;
import com.spring.mvc.chap05.repository.BoardMapper;
import com.spring.mvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.spring.mvc.util.LoginUtil.isAdmin;

//인터셉터: 하위 컨트롤러에 요청이 들어가기 전 or 후에 공통으로 검사할 일들을 정의해놓는 클래스
//게시판 관련 인가 처리
@Configuration
@Slf4j
@RequiredArgsConstructor
public class BoardInterceptor implements HandlerInterceptor {
    private final BoardMapper boardMapper;
    @Override
    public boolean preHandle(
            HttpServletRequest request
            , HttpServletResponse response
            , Object handler
    )throws Exception{
//        로그인을 헀는지 확인
        HttpSession session = request.getSession();
        if(!LoginUtil.isLogin(session)){
                    log.info("this request({}) denied", request.getRequestURI());
                    //        로그인 안했으면 로그인페이지로 강제로 리다이렉션
                   response.sendRedirect("/members/sign-in");
                    return false;
                }

//                삭제요청이 있을 때 관리자인지 작성자인지 체크 할 것
        /*
        * 1. 우선 로그인 계정과 삭제하려는 게시물의 계정명이 일치해야함.
        * 2. 로그인 계정은 세션에서 구할 수 있고
        * 3. 삭제하려는 게시물의 계정은 어디서 가져올지 생각(삭제요청에는 대상 게시글의 글번호가 있음.)
        * 4.  글 번호를 DB에 조회해서 계정명을 얻어낸다.
        * */
//        삭제 요청인지 먼저 확인.
        String uri = request.getRequestURI();
        if(uri.contains("delete")) {
//            쿼리 파라미터 읽기
            int bno = Integer.parseInt(request.getParameter("bno"));
            Board board = boardMapper.findOne(bno);

            String targetAccount = board.getAccount();
//            관리자라면 그대로 통과
        if(isAdmin(session)) return true;

//            작성자의 글이 아니면 redirect.
        if(!LoginUtil.isMine(session, targetAccount)){
            response.sendRedirect("/access-deny");
            return false;
        }

        }


        log.info("board interceptor pass!");
        return true;
    }


}
