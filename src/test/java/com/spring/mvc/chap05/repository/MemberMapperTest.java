package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap05.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMapperTest {
   @Autowired
    MemberMapper memberMapper;

   @Autowired
    PasswordEncoder encoder;

    @Test
    @DisplayName("회원가입에 성공")
    void registerTest() {
        Member member = Member.builder()
                .account("chundo")
                .password(encoder.encode("peach11"))
                .name("천도복숭아")
                .email("peachch@email.com")
                .build();
        boolean flag = memberMapper.save(member);
        assertTrue(flag);

    }

    @Test
    @DisplayName("backdo라는 계정명으로 회원조회하면 그회원 이름이 복숭아")
    void findMemberTest() {
        //given
        String acc = "backdo";

        //when
        Member foundMember = memberMapper.findMember(acc);
        //then
        System.out.println("foundMember = " + foundMember);
        assertEquals("복숭아", foundMember.getName());
    }

    @Test
    @DisplayName("계정이 backdo인 경우 결과값이 1이 나오는지 확인")
    void accountDuplicateTest() {
        String acc = "backdo";

        int count = memberMapper.isDuplicate("account", acc);

        assertEquals(1, count);
//        테스트 실패도 실행해볼것.

    }
    @Test
    @DisplayName("이메일이 peach@email.com인 경우 결과값이 1이 나오는지 확인")
    void emailDuplicateTest() {
        String email = "peach@email.com";

        int count = memberMapper.isDuplicate("email", email);

        assertEquals(1, count);
//        테스트 실패도 실행해볼것.

    }

    @Test
    @DisplayName("비밀번호가 암호화 되어야 한다")
    void encodingTest() {
//        인코딩 전 패스워드
        String rawPassword = "abc1234@";

//        인코딩후 패스워드
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("rawPassword = " + rawPassword);
        System.out.println("encodedPassword = " + encodedPassword);
    }

}