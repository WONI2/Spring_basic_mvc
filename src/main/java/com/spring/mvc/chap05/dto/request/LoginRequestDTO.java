package com.spring.mvc.chap05.dto.request;

import lombok.*;

@Setter @NoArgsConstructor
@Getter @ToString @EqualsAndHashCode

public class LoginRequestDTO {
    private String account;
    private String password;
    private boolean autoLogin;


}
