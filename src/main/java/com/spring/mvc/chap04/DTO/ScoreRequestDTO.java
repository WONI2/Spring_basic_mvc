package com.spring.mvc.chap04.DTO;

import lombok.*;

@Setter @Getter @ToString @NoArgsConstructor
@AllArgsConstructor @EqualsAndHashCode
//dto를 만들 때 @Setter @Getter @NoArgsConstructor 는 필수
//spring은 setter와 기본생성자를 사용하기 때문 (allarg~를 쓰지 않음. )
public class ScoreRequestDTO {
    private String name;
    private int kor, eng, math;





}
