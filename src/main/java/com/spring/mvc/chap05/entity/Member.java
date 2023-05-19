package com.spring.mvc.chap05.entity;

import lombok.*;

import java.time.LocalDateTime;
@Setter @Getter @ToString @EqualsAndHashCode
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Member {
    private String account;
    private String password;
    private String name;
    private String email;
    private Auth auth;
    private LocalDateTime regDate;

    private LocalDateTime limitTime;

    private String profileImg; //DB에 profile 이미지 경로를 저장.

    private LoginMethod loginMethod;
}
