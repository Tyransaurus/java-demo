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
        model.addAttribute("message", "This is a demo to demonstrate Java skills and AWS server services.");
        model.addAttribute("banner", bannerService.readBanner());
        return ("index"); // this goes to look for templates 
    }

    @PostMapping("/admin/banner")
    public String updateBanner(@RequestParam String text)
    {
        bannerService.writeBanner(text);
        return "redirect:/";
    }
    
    @PostMapping("/admin/banner/reset")
    public String resetBanner() {
        bannerService.resetBanner();
        return "redirect:/";
    }
}

