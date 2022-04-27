package my.self.springapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import my.self.springapp.domain.TestService;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService service;


    @GetMapping("/make")
    @ResponseBody
    public String make() {
        service.makeTest();
        return "Ok";
    }


}
