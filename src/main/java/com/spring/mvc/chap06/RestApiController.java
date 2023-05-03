package com.spring.mvc.chap06;

import com.spring.mvc.jdbc.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rests")
public class RestApiController {

    @GetMapping("/hello")

    public String hello() {

        return "HELLO!";
    }

    @GetMapping("/foods")
    public List<String> foods() {
//        String[] foodList = {"탕수육", "유산슬", "유린기"};
        List<String> foodList = List.of("탕수육", "유산슬", "유린기");
        return foodList;
    }

    @GetMapping("/person")
    public Person person () {
        Person p = new Person(1L, "에디", 3);
        return p;
    }
    @GetMapping("/person-list")
    public ResponseEntity<?> personList () {
        Person p1 = new Person(1L, "에디", 3);
        Person p2 = new Person(2L, "크롱", 1);
        Person p3 = new Person(3L, "포비", 5);
        List<Person> personList =  List.of(p1,p2,p3);
        return ResponseEntity.ok().body(personList);
    }
    @GetMapping("/bmi")
    public ResponseEntity<?> bmi(@RequestParam(required = false) Double cm,
                                 @RequestParam(required = false) Double kg) {
        if(cm == null || kg == null) {
            return ResponseEntity.badRequest().body("키와 몸무게가 없습니다");
//        정해놓지 않으면 스프링에서 정해놓은 코드로만 보여짐.
        }
        double bmi =  kg / (cm/100) * (cm/100);
        return ResponseEntity.ok().body(bmi);
    }


}
