package com.spring.mvc.mybatis;

import com.spring.mvc.jdbc.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PersonMapperTest {

    @Autowired
    PersonMapper mapper;

    @Test
    @DisplayName("마이바티스 매퍼로 사람정보 저장에 성공")
    void saveTest() {
        //given
        Person p = Person.builder()
                .personName("바나나")
                .personAge(2)
                .build();
//        when
        boolean flag = mapper.save(p);

//        then
        assertTrue(flag);
    }
    @Test
    @DisplayName("마이바티스 매퍼로 사람정보 수정에 성공")
    void modifyTest() {
        //given
        Person p = Person.builder()
                .personName("루피")
                .personAge(4)
                .id(5L)
                .build();
//        when
        boolean flag = mapper.modify(p);

//        then
        assertTrue(flag);
    }
    @Test
    @DisplayName("삭제에 성공할 것")
    void removeTest(){
        long id = 6L;
        boolean flag = mapper.remove(id);
        assertTrue(flag);

    }
    @Test
    @DisplayName("사람정보 전체조회")
    void findAllTest(){

        //when
        List<Person> people = mapper.findAll();
        for (Person person : people) {
            System.out.println("person = " + person);
        }

        assertEquals(4, people.size());


    }
    @Test
    @DisplayName("사람정보 전체조회")
    void findOneTest(){
        long id = 1L;
        //when
        Person one = mapper.findOne(id);
        System.out.println("one = " + one);

        assertEquals("크롱",one.getPersonName());
        assertNotNull(one);

    }

}