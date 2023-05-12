package com.spring.mvc.chap05.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@NoArgsConstructor
//위의 2개는 필수
@Getter @ToString
@EqualsAndHashCode


public class SignUpRequestDTO {
    @NotBlank
    @Size(min=4, max=14)
    private String account;
    @NotBlank
    private String password;
    @NotBlank
    @Size(min=2, max=6)
    private String name;
    @NotBlank
    @Email
    private String email;



}