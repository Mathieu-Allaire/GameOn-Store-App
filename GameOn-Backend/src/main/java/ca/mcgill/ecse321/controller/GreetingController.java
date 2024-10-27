package ca.mcgill.ecse321.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class GreetingController {
    @GetMapping("/")
    public String index() {
        return "Welcome to the GameOn API!";
    }

    @GetMapping("/error")
    public String error() {
        return "An error occurred!";
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World!";
    }

    @GetMapping("/hello/{myName}")
    public String helloWithArgs(@PathVariable String myName, @RequestParam String yourName) {
        return String.format("Hello, %s!",yourName, myName);
    }
        
}
