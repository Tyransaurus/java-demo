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
     public String viewChallenges(Model model) {
        // Initialize an empty result for the first page load
        model.addAttribute("page", "challenges");
        model.addAttribute("twoSumResult", "");
        model.addAttribute("reverseStringResult", "");
        return "challenges";
    }

    @PostMapping("/challenges/two-sum")
    public String twoSum(@RequestParam String numbers,
                     @RequestParam int target,
                     Model model,
                     @RequestParam(required = false, defaultValue = "") String reverseStringResult) {
    try {
        String[] parts = numbers.split("\\s*,\\s*");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i].trim());
        }

        var res = challengeService.twoSum(arr, target);
        model.addAttribute("twoSumResult", res.isEmpty()
            ? "No solution found."
            : String.join("<br/>", res));  // join with line breaks
    } catch (NumberFormatException e) {
        model.addAttribute("twoSumResult", "Invalid number format. Please enter comma-separated integers.");
    }

    // Keep existing results
    model.addAttribute("reverseStringResult", reverseStringResult);
    return "challenges";
}

    @PostMapping("/challenges/reverse")
    public String reverse(@RequestParam String text, 
                          Model model,
                          @RequestParam(required = false, defaultValue = "") String twoSumResult) {
        model.addAttribute("reverseStringResult", challengeService.reverseString(text));

        // Keep existing results to display both on the same page
        model.addAttribute("twoSumResult", twoSumResult);
        return "challenges";
    }
}
