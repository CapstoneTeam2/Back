package com.neighborcharger.capstoneproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @GetMapping({"","/"})
    public String index(){
        return "Index";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/login")
    public @ResponseBody String login(){
        return "login";
    }

    @GetMapping("/loginProc")
    public String loginProc(){
        return "loginProc";
    }

    @GetMapping("/join")
    public @ResponseBody String join(){
        return "join";
    }
    @GetMapping("/joinProc")
    public String joinProc(){
        return "joinProc";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }
}
