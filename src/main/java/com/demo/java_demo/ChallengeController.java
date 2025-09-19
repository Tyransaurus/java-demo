package com.demo.java_demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@SessionAttributes("vm")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    // Create the session-scoped view-model once
    @ModelAttribute("vm")
    public ChallengeViewModel vm() {
        return new ChallengeViewModel();
    }

    @GetMapping("/challenges")
    public String viewChallenges(@ModelAttribute("vm") ChallengeViewModel vm, Model model) {
        model.addAttribute("page", "challenges");
        return "challenges";
    }

    // ---- TWO SUM -----------------------------------------------------------
    @PostMapping("/challenges/two-sum")
    public String twoSum(@RequestParam String numbers,
                         @RequestParam int target,
                         @ModelAttribute("vm") ChallengeViewModel vm,
                         Model model,
                         @RequestParam(defaultValue = "false") boolean fragment) {
        model.addAttribute("page", "challenges");
        try {
            String[] parts = numbers.split("\\s*,\\s*");
            int[] arr = new int[parts.length];
            for (int i = 0; i < parts.length; i++) arr[i] = Integer.parseInt(parts[i].trim());

            var res = challengeService.twoSum(arr, target);
            vm.twoSumResult = res.isEmpty() ? "No solution found." : String.join("<br/>", res);
        } catch (NumberFormatException e) {
            vm.twoSumResult = "Invalid number format. Please enter comma-separated integers.";
        }
        return fragment ? "challenges :: twoSumResultFrag" : "challenges";
    }

    // ---- REVERSE -----------------------------------------------------------
    @PostMapping("/challenges/reverse")
    public String reverse(@RequestParam String text,
                          @ModelAttribute("vm") ChallengeViewModel vm,
                          Model model,
                          @RequestParam(defaultValue = "false") boolean fragment) {
        model.addAttribute("page", "challenges");
        vm.reverseStringResult = challengeService.reverseString(text);
        return fragment ? "challenges :: reverseResultFrag" : "challenges";
    }

    // ---- VALID PARENS ------------------------------------------------------
    @PostMapping("/challenges/valid-parentheses")
    public String validParentheses(@RequestParam String text,
                                   @ModelAttribute("vm") ChallengeViewModel vm,
                                   Model model,
                                   @RequestParam(defaultValue = "false") boolean fragment) {
        model.addAttribute("page", "challenges");
        boolean ok = challengeService.validParentheses(text);
        vm.validParenthesesResult = ok ? "Balanced ✅" : "Not balanced ❌";
        return fragment ? "challenges :: validParensResultFrag" : "challenges";
    }

    // ---- ANAGRAMS ----------------------------------------------------------
    @PostMapping("/challenges/anagrams")
    public String anagrams(@RequestParam String words,
                           @ModelAttribute("vm") ChallengeViewModel vm,
                           Model model,
                           @RequestParam(defaultValue = "false") boolean fragment) {
        model.addAttribute("page", "challenges");
        String[] parts = words.split("[,\\s]+");
        vm.anagramsResult = challengeService.groupAnagrams(Arrays.asList(parts));
        return fragment ? "challenges :: anagramsResultFrag" : "challenges";
    }

    // ---- LONGEST SUBSTRING -------------------------------------------------
    @PostMapping("/challenges/longest-substring")
    public String longestSubstring(@RequestParam String text,
                                   @ModelAttribute("vm") ChallengeViewModel vm,
                                   Model model,
                                   @RequestParam(defaultValue = "false") boolean fragment) {
        model.addAttribute("page", "challenges");
        int len = challengeService.lengthOfLongestSubstring(text);
        vm.longestSubstringResult = "Length = " + len;
        return fragment ? "challenges :: longestSubstringResultFrag" : "challenges";
    }

    // ---- MERGE INTERVALS ---------------------------------------------------
    @PostMapping("/challenges/merge-intervals")
    public String mergeIntervals(@RequestParam String ranges,
                                 @ModelAttribute("vm") ChallengeViewModel vm,
                                 Model model,
                                 @RequestParam(defaultValue = "false") boolean fragment) {
        model.addAttribute("page", "challenges");
        List<int[]> input = new ArrayList<>();
        for (String token : ranges.split("\\s*,\\s*")) {
            String[] p = token.split("\\s*-\\s*");
            if (p.length == 2) input.add(new int[]{Integer.parseInt(p[0]), Integer.parseInt(p[1])});
        }
        var merged = challengeService.mergeIntervals(input);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < merged.size(); i++) {
            int[] it = merged.get(i);
            sb.append("[").append(it[0]).append(", ").append(it[1]).append("]");
            if (i < merged.size() - 1) sb.append(", ");
        }
        vm.mergeIntervalsResult = sb.toString();
        return fragment ? "challenges :: mergeIntervalsResultFrag" : "challenges";
    }
}