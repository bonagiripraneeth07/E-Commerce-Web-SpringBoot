package com.example.ecomboot;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class helloContainer {

@GetMapping("hello")
    public String greet(){
    return "hello";
}

}
