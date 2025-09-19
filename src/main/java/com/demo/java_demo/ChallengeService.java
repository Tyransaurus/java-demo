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

    // Valid Parentheses: Checks if a string has matching brackets
    public boolean validParentheses(String s) {
        if (s == null) return false;
        Map<Character, Character> map = Map.of(')', '(', ']', '[', '}', '{');
        Deque<Character> st = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (map.containsValue(c)) st.push(c);
            else if (map.containsKey(c)) {
                if (st.isEmpty() || st.pop() != map.get(c)) return false;
            }
        }
        return st.isEmpty();
    }

    // Group Anagrams: groups words that are anagrams
    public List<List<String>> groupAnagrams(List<String> words) {
        Map<String, List<String>> buckets = new HashMap<>();
        for (String w : words) {
            char[] a = w.toLowerCase().toCharArray();
            Arrays.sort(a);
            String key = new String(a);
            buckets.computeIfAbsent(key, k -> new ArrayList<>()).add(w);
        }
        return new ArrayList<>(buckets.values());
    }

    // Longest Substring Without Repeating Characters
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> last = new HashMap<>();
        int left = 0, best = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (last.containsKey(c) && last.get(c) >= left) {
                left = last.get(c) + 1;
            }
            last.put(c, right);
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    // Merge Intervals
    public List<int[]> mergeIntervals(List<int[]> intervals) {
        if (intervals == null || intervals.isEmpty()) return List.of();
        intervals.sort(Comparator.comparingInt(a -> a[0]));
        List<int[]> out = new ArrayList<>();
        int[] cur = intervals.get(0).clone();
        for (int i = 1; i < intervals.size(); i++) {
            int[] nxt = intervals.get(i);
            if (nxt[0] <= cur[1]) { // overlap
                cur[1] = Math.max(cur[1], nxt[1]);
            } else {
                out.add(cur);
                cur = nxt.clone();
            }
        }
        out.add(cur);
        return out;
    }
}
