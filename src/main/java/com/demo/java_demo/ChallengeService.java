package com.demo.java_demo;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ChallengeService 
{

    /**
     * Finds two numbers in an array that add up to a specific target.
     * Assumes there is exactly one solution.
     * 
     * @param nums the array of integers.
     * @param target target sum.
     * @return A list containing the alphabetized list of the two numbers that finds the target.
     */
    public List<String> twoSum(int[] nums, int target) {
        List<String> results = new ArrayList<>();
        Map<Integer, List<Integer>> seen = new HashMap<>(); // value -> list of indices where it appeared

        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            int need = target - current;

            // If we've seen the complement, pair it with current
            if (seen.containsKey(need)) {
                for (int j : seen.get(need)) {
                    results.add(need + ", " + current + " (" + j + ", " + i + ")");
                }
            }

            // Add this index to the map for the current value
            seen.computeIfAbsent(current, k -> new ArrayList<>()).add(i);
        }

        return results;
    }

    // returns a string but reversed
    public String reverseString(String s)
    {
        return new StringBuilder(s).reverse().toString();
    }
}
