package com.studygroupfinder.finals.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
//Redirect to users/login
    @GetMapping("/")
    public String home(HttpServletRequest request) {
        System.out.println("Accessed path: " + request.getRequestURI());
        System.out.println("Query string: " + request.getQueryString());
        return "redirect:users/login";
    }
//for testing purposes
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Test endpoint reached";
    }
}
