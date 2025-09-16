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
    public List<Integer> twoSum(int [] nums, int target)
    {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++)
        {
            int need = target - nums[i];
            if (map.containsKey(need)) return List.of(map.get(need), i);
            map.put(nums[i], i);
        }

        return List.of();
    }

    // returns a string but reversed
    public String reverseString(String s)
    {
        return new StringBuilder(s).reverse().toString();
    }
}
