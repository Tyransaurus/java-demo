package com.demo.java_demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;;;

@Controller
public class HomeController 
{

    
    @GetMapping("/")
    public String index(Model model)
    {
        model.addAttribute("title", "DJ's Java Demo");
        model.addAttribute("message", "Hello from Spring Boot!");
        return ("index"); // this goes to look for templates 
    }
}
