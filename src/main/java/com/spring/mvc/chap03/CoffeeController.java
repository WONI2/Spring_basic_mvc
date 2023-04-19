package com.spring.mvc.chap03;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/coffee")
public class CoffeeController {
    /**
     * @requst : /coffee/order : get
     * @response : /chap03/coffee-form.jsp
     */
    @GetMapping("/order")
    public String coffeeOrder() {



        System.out.println("/coffee/order : get요청 발생");
        return "chap03/coffee-form";
    }
/*
* @request : /coffee/result : post
* @response : /chap03/coffee-result.jsp
*
* */
    @PostMapping("/result")
    public String coffeeResult(String menu,
                               @RequestParam(defaultValue = "3000") int price, Model model) {
        System.out.println("menu = " + menu);

        model.addAttribute("menu", menu);
        model.addAttribute("p", price);






        return "chap03/coffee-result";
    }


}
