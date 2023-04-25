package com.spring.mvc.jdbc;

import lombok.*;

//이 클래스는 데이터베이스 테이블과 연동을 위한 객체
//DB의 table의 컬럼과 1:1로 매칭되는 필드를 적어주세요.
@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class Person {
    private long id;
    private String personName;
    private int personAge;

 //db에서 받아오는 것들을 담아주는 역할.

}
