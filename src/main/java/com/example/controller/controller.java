package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
public class controller {

    @GetMapping("/hello")
    public String handle(){
        return "index";
    }

    @GetMapping("/step1")
    public String handle2(@PathVariable String value){
        return "step1";
    }

    @GetMapping("/step2")
    public String handle3(@PathVariable String value){
        return "step2";
    }
}
