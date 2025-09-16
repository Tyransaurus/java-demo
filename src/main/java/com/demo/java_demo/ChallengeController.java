package com.demo.java_demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChallengeController 
{
    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping("/challenges")
    public String view(Model model) {
        model.addAttribute("result", "");
        return "challenges";
    }

    @PostMapping("/challenges/two-sum")
    public String twoSum(@RequestParam String numbers, 
                         @RequestParam int target, 
                         Model model) {
        String[] parts = numbers.split("\\s*,\\s*");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) arr[i] = Integer.parseInt(parts[i]);

        var res = challengeService.twoSum(arr, target);
        model.addAttribute("result", res.isEmpty() ? "No solution" : "Indices: " + res);
        return "challenge";
    }
    
    @PostMapping("/challenge/reverse")
    public String reverse(@RequestParam String text, Model model) {
        model.addAttribute("result", challengeService.reverseString(text));
        return "challenge";
    }
}
