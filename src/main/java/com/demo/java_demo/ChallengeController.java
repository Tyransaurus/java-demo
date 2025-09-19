package com.demo.java_demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChallengeController {
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
            model.addAttribute("twoSumResult", res.isEmpty() ?
                "No solution found." :
                String.join("<br/>", res)); // join with line breaks
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

    @PostMapping("/challenges/valid-parentheses")
    public String validParentheses(
        @RequestParam String text,
        Model model,
        @RequestParam(required = false, defaultValue = "") String twoSumResult,
        @RequestParam(required = false, defaultValue = "") String reverseStringResult) {

        boolean ok = challengeService.validParentheses(text);
        model.addAttribute("validParenthesesResult", ok ? "Balanced ✅" : "Not balanced ❌");

        // keep other results
        model.addAttribute("page", "challenges");
        model.addAttribute("twoSumResult", twoSumResult);
        model.addAttribute("reverseStringResult", reverseStringResult);
        return "challenges";
    }

    @PostMapping("/challenges/anagrams")
    public String anagrams(
        @RequestParam String words, // comma- or space-separated
        Model model,
        @RequestParam(required = false, defaultValue = "") String twoSumResult,
        @RequestParam(required = false, defaultValue = "") String reverseStringResult,
        @RequestParam(required = false, defaultValue = "") String validParenthesesResult) {

        String[] parts = words.split("[,\\s]+");
        var groups = challengeService.groupAnagrams(Arrays.asList(parts));
        model.addAttribute("anagramsResult", groups); // List<List<String>>

        model.addAttribute("page", "challenges");
        model.addAttribute("twoSumResult", twoSumResult);
        model.addAttribute("reverseStringResult", reverseStringResult);
        model.addAttribute("validParenthesesResult", validParenthesesResult);
        return "challenges";
    }

    @PostMapping("/challenges/longest-substring")
    public String longestSubstring(
        @RequestParam String text,
        Model model,
        @RequestParam(required = false, defaultValue = "") String twoSumResult,
        @RequestParam(required = false, defaultValue = "") String reverseStringResult,
        @RequestParam(required = false, defaultValue = "") String validParenthesesResult) {

        int len = challengeService.lengthOfLongestSubstring(text);
        model.addAttribute("longestSubstringResult", "Length = " + len);

        model.addAttribute("page", "challenges");
        model.addAttribute("twoSumResult", twoSumResult);
        model.addAttribute("reverseStringResult", reverseStringResult);
        model.addAttribute("validParenthesesResult", validParenthesesResult);
        return "challenges";
    }

    @PostMapping("/challenges/merge-intervals")
    public String mergeIntervals(
        @RequestParam String ranges, // e.g. "1-3,2-6,8-10,15-18"
        Model model,
        @RequestParam(required = false, defaultValue = "") String twoSumResult,
        @RequestParam(required = false, defaultValue = "") String reverseStringResult,
        @RequestParam(required = false, defaultValue = "") String validParenthesesResult,
        @RequestParam(required = false, defaultValue = "") String longestSubstringResult) {

        List < int[] > input = new ArrayList < > ();
        for (String token: ranges.split("\\s*,\\s*")) {
            String[] p = token.split("\\s*-\\s*");
            if (p.length == 2) input.add(new int[] {
                Integer.parseInt(p[0]), Integer.parseInt(p[1])
            });
        }
        var merged = challengeService.mergeIntervals(input);

        // Pretty-print like: [1,6], [8,10], [15,18]
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < merged.size(); i++) {
            int[] it = merged.get(i);
            sb.append("[").append(it[0]).append(", ").append(it[1]).append("]");
            if (i < merged.size() - 1) sb.append(", ");
        }
        model.addAttribute("mergeIntervalsResult", sb.toString());

        model.addAttribute("page", "challenges");
        model.addAttribute("twoSumResult", twoSumResult);
        model.addAttribute("reverseStringResult", reverseStringResult);
        model.addAttribute("validParenthesesResult", validParenthesesResult);
        model.addAttribute("longestSubstringResult", longestSubstringResult);
        return "challenges";
    }


}