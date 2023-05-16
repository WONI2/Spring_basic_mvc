package com.spring.mvc.chap05.service;


import com.spring.mvc.chap05.dto.AutoLoginDTO;
import com.spring.mvc.chap05.dto.request.LoginRequestDTO;
import com.spring.mvc.chap05.dto.response.LoginUserResponseDTO;
import com.spring.mvc.chap05.dto.request.SignUpRequestDTO;
import com.spring.mvc.chap05.entity.Member;
import com.spring.mvc.chap05.repository.MemberMapper;
import com.spring.mvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder encoder;

//    회원가입 처리 서비스
    public boolean join(final SignUpRequestDTO dto, final String savePath) {
//        dto를 entity로 변환
        Member member = Member.builder()
                .account(dto.getAccount())
                .name(dto.getName())
                .password(encoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .profileImg(savePath)
                .build();

//        mapper에게 회원 정보 전달해서 저장 명령 내리기
        return  memberMapper.save(member);

    }

//    중복검사 서비스 처리
    public boolean checkSignUpValue(String type, String keyword){
        int flagNum = memberMapper.isDuplicate(type, keyword);

        return flagNum == 1;

    }

//로그인 검증
    public LoginResult authenticate(LoginRequestDTO dto,
                                    HttpSession session,
                                    HttpServletResponse response)  {
        Member foundMember = memberMapper.findMember(dto.getAccount());
//       회원가입 여부 확인
        if(foundMember == null) {
            log.info("{} -회원가입이 안되어 있음",dto.getAccount());
            return LoginResult.NO_ACC;
        }
//      비밀번호 일치 확인 내부적으로 비교해서 인코딩해주는 기능
        if(!encoder.matches(dto.getPassword(), foundMember.getPassword())) {
            log.info("비밀번호 불일치");
            return LoginResult.NO_PW;
        }

//        자동로그인 체크 여부 확인
        if(dto.isAutoLogin()) {
//            1. 쿠키생성 - 쿠키 값에 세션 아이디를 저장
            Cookie autoLoginCookie = new Cookie(LoginUtil.AUTO_LOGIN_COOKIE,session.getId() );
//            2. 쿠키 셋팅 - 수명, 사용경로
            int limitTime = 60*60*24*90; //90일
            autoLoginCookie.setMaxAge(limitTime);
            autoLoginCookie.setPath("/");//전체 경로에서 들고 다니게 함. 어디서든지 자동로그인으로 들어갈 수 있게

//            3. 쿠키를 클라이언트에 응답전송
            response.addCookie(autoLoginCookie);
//            4. DB에도 쿠키에 저장된 값과 수명을 저장
            memberMapper.saveAutoLogin(AutoLoginDTO.builder()
                            .sessionId(session.getId())
                            .account(dto.getAccount())
                            .limitTime(LocalDateTime.now().plusDays(90))
                    .build());
//            세션아이디가 바뀌어도 자동로그인할 때의 쿠키값은 유지 될 것

        }


        log.info("{}님 로그인 성공",foundMember.getName());
        return LoginResult.SUCCESS;
    }

    public void maintainLoginState(HttpSession session, String account) {
//        로그인이 성공하면 세션에 로그인한 회원의 정보들을 저장
        /*
        * 로그인 시 클라이언트에게 전달할 회원정보
        * - 닉네임
        * - 프로필 사진
        * - 마지막 로그인 시간
        *
        * */
//현재 로그인한 사람이 모든 정보
        Member member = getMember(account);

//        현재 로그인 한 사람의 일부정보
        LoginUserResponseDTO dto = LoginUserResponseDTO.builder()
                .account(member.getAccount())
                .nickName(member.getName())
                .email(member.getEmail())
                .auth(member.getAuth().toString()) // auth는 enum 이니까
                .profile(member.getProfileImg())
                .build();

//       정보를 세션에 저장
        session.setAttribute(LoginUtil.LOGIN_KEY,dto);
//세션의 수명 설정
        session.setMaxInactiveInterval(60*60); // 1시간 설정 함. 설정 안하면 기본값 30분

    }
//        멤버정보를 가져오는 서비스 기능
    public Member getMember(String account){
        return memberMapper.findMember(account);
    }


    public void autoLoginClear(HttpServletRequest request, HttpServletResponse response) {
//    1. 자동 로그인 해지 - 자동 로그인 쿠키 가져오기
        Cookie cookie = WebUtils.getCookie(request, LoginUtil.AUTO_LOGIN_COOKIE);

//        2. 쿠키를 삭제한다
//        쿠키의 수명을 0초로 만들어서 다시 클라이언트에게 응답


        if (cookie != null) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
//    3. 데이터 베이스에서도 자동로그인 해제
        memberMapper.saveAutoLogin(AutoLoginDTO.builder()
                        .sessionId("none")
                        .limitTime(LocalDateTime.now())
                        .account(LoginUtil.getCurrentLoginMemberAccount(request.getSession()))
                        .build());
    }
}
