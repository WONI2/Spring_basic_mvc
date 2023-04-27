package com.spring.mvc.etc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void test() {
        Employee e = Employee.builder()
                .empName("바나나")
                .build();
        Actor actor = Actor.builder()
                .actorName("고구마")
                .build();

    }

}