package com.spring.mvc.spring_jdbc;

import com.spring.mvc.jdbc.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonSpringRepositoryTest {
    @Autowired
    PersonSpringRepository repository;

    @Test
    void savePersonTest() {
        //given
        Person p = new Person();
        p.setPersonName("뽀로로");
        p.setPersonAge(3);
        //when
        repository.savePerson(p);
    }
    @Test
    void removePersonTest() {
        long id = 3L;

        repository.removePerson(id);
    }
    @Test
    void modifyPersonTest() {
//        long id = 5L;
        Person p = new Person();
        p.setId(5L);
        p.setPersonName("루피");
        p.setPersonAge(4);

        boolean flag = repository.modifyPerson(p);
        //then
        assertTrue(flag);
    }

    @Test
    void findAllTest() {
        List<Person> people = repository.findAll();
        for (Person person : people) {
            System.out.println("person = " + person);
        }
    }

    @Test
    void findOneTest() {
        Person p = repository.findOne(1L);
        System.out.println("p = " + p);
        assertEquals("크롱", p.getPersonName());

    }
    
}