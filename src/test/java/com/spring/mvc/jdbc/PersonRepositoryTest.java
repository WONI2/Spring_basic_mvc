package com.spring.mvc.jdbc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {
    @Autowired
    PersonRepository repository;

    @Test
    @DisplayName("사람의 이름과 나이 정보를 db table에 삽입하기")

    void saveTest() {
//        given
        Person p = new Person();
        p.setPersonName("포비");
        p.setPersonAge(10);
//        when
        repository.save(p);
//        then
    }
        @Test
        @DisplayName("사람의 이름과 나이 정보를 수정해야한다")

        void updateTest() {
//        given
            Person p = new Person();
            p.setPersonName("롱롱");
            p.setPersonAge(5);
            p.setId(1L);
//        when
            repository.update(p);
//        then

        }

    @Test

    void removeTest() {
//        given
       Long id = 1L;
//        when
        repository.remove(id);
//        then
    }


    @Test
    void findAllTest(){
        List<Person> all = repository.findAll();
        for (Person p : all) {
            System.out.println("p = " + p);
        }

    }
@Test
    void findOneTest() {
    Person p = repository.findOne(1L);
    System.out.println("p = " + p);
}




}