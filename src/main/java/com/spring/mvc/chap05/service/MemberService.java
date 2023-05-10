package com.spring.mvc.chap05.service;


import com.spring.mvc.chap05.dto.LoginRequestDTO;
import com.spring.mvc.chap05.dto.SignUpRequestDTO;
import com.spring.mvc.chap05.entity.Member;
import com.spring.mvc.chap05.repository.MemberMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder encoder;

//    회원가입 처리 서비스
    public boolean join(final SignUpRequestDTO dto) {
//        dto를 entity로 변환
        Member member = Member.builder()
                .account(dto.getAccount())
                .name(dto.getName())
                .password(encoder.encode(dto.getPassword()))
                .email(dto.getEmail())
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
    public LoginResult authenticate(LoginRequestDTO dto)  {
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
        log.info("{}님 로그인 성공",foundMember.getName());
        return LoginResult.SUCCESS;
    }
}
