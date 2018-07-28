package com.fahamutech.smartstock.webservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @RequestMapping("/")
    public String home(){
        return "home";
    }
}
