package com.demo.java_demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
;;

@Controller
public class HomeController 
{

    private final BannerService bannerService;

    public HomeController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping("/")
    public String index(Model model)
    {
        model.addAttribute("page", "home");
        model.addAttribute("title", "DJ's Java Demo");
        model.addAttribute("message", "Hello from Spring Boot!");
        model.addAttribute("banner", bannerService.readBanner());
        return ("index"); // this goes to look for templates 
    }

    @PostMapping("/admin/banner")
    public String updateBanner(@RequestParam String text)
    {
        bannerService.writeBanner(text);
        return "redirect:/";
    }
    
}
