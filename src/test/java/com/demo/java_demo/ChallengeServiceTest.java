package com.demo.java_demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class ChallengeServiceTest {

    private final ChallengeService svc = new ChallengeService();

    // ------------------------ reverseString ------------------------

    @Test
    @DisplayName("reverseString: basic")
    void reverse_basic() {
        assertEquals("cba", svc.reverseString("abc"));
    }

    @Test
    @DisplayName("reverseString: empty")
    void reverse_empty() {
        assertEquals("", svc.reverseString(""));
    }

    @Test
    @DisplayName("reverseString: unicode")
    void reverse_unicode() {
        assertEquals("🙂界世 olleH", svc.reverseString("Hello 世界🙂"));
    }

    // --------------------------- twoSum ----------------------------

    @Test
    @DisplayName("twoSum: single pair")
    void twoSum_singlePair() {
        int[] arr = {2, 7, 11, 15};
        var actual = svc.twoSum(arr, 9);
        var expected = Set.of(pair(2, 7, 0, 1));
        assertPairsEqual(expected, actual);
    }

    @Test
    @DisplayName("twoSum: multiple pairs")
    void twoSum_multiplePairs() {
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        var actual = svc.twoSum(arr, 11);
        var expected = Set.of(
                pair(1,10,0,9),
                pair(2, 9,1,8),
                pair(3, 8,2,7),
                pair(4, 7,3,6),
                pair(5, 6,4,5)
        );
        assertPairsEqual(expected, actual);
    }

    @Test
    @DisplayName("twoSum: no solution")
    void twoSum_noSolution() {
        int[] arr = {1, 2, 5};
        var actual = svc.twoSum(arr, 100);
        assertTrue(actual == null || actual.isEmpty(), "Expected no pairs");
    }

    @Test
    @DisplayName("twoSum: negatives (multiple valid pairs)")
    void twoSum_negatives() {
        int[] arr = {-4, -1, 0, 2, 5};
        var actual = svc.twoSum(arr, 1); // -1+2 and -4+5
        var expected = Set.of(
                pair(-1, 2, 1, 3),
                pair(-4, 5, 0, 4)
        );
        assertPairsEqual(expected, actual);
    }

    @Test
    @DisplayName("twoSum: duplicates allowed (distinct indices)")
    void twoSum_duplicates() {
        int[] arr = {3, 3, 4, 5};
        var actual = svc.twoSum(arr, 6);
        var expected = Set.of(pair(3, 3, 0, 1));
        assertPairsEqual(expected, actual);
    }

    // --------------------- validParentheses ------------------------

    @Test
    @DisplayName("validParentheses: balanced")
    void validParentheses_ok() {
        assertTrue(svc.validParentheses("{[()()]}"));
    }

    @Test
    @DisplayName("validParentheses: not balanced")
    void validParentheses_bad() {
        assertFalse(svc.validParentheses("([)]"));
    }

    // ----------------------- groupAnagrams -------------------------

    @Test
    @DisplayName("groupAnagrams: classic groups")
    void groupAnagrams_groups() {
        var groups = svc.groupAnagrams(List.of("eat","tea","tan","ate","nat","bat"));
        // Normalize to sets to ignore order of groups and items
        var sets = new ArrayList<Set<String>>();
        for (var g : groups) sets.add(new HashSet<>(g));
        assertTrue(sets.contains(Set.of("eat","tea","ate")));
        assertTrue(sets.contains(Set.of("tan","nat")));
        assertTrue(sets.contains(Set.of("bat")));
    }

    // ---------------- lengthOfLongestSubstring ---------------------

    @Test
    @DisplayName("lengthOfLongestSubstring: examples")
    void longestSubstring_examples() {
        assertEquals(3, svc.lengthOfLongestSubstring("abcabcbb"));
        assertEquals(1, svc.lengthOfLongestSubstring("bbbbb"));
        assertEquals(3, svc.lengthOfLongestSubstring("pwwkew"));
        assertEquals(0, svc.lengthOfLongestSubstring(""));
    }

    // ------------------------ mergeIntervals -----------------------

    @Test
    @DisplayName("mergeIntervals: basic merge")
    void mergeIntervals_basic() {
        var input = new ArrayList<>(List.of(
                new int[]{1,3}, new int[]{2,6}, new int[]{8,10}, new int[]{15,18}
        ));
        var merged = svc.mergeIntervals(input);
        assertArrayEquals(new int[]{1,6}, merged.get(0));
        assertArrayEquals(new int[]{8,10}, merged.get(1));
        assertArrayEquals(new int[]{15,18}, merged.get(2));
        assertEquals(3, merged.size());
    }

    // =========================== Helpers ===========================

    // For twoSum: unify formatting like "a, b (i, j)"
    private record Pair(int a, int b, int i, int j) {}

    private Pair pair(int a, int b, int i, int j) {
        // canonicalize so (a,b) and (b,a) compare equal; indices sorted too
        if (a > b) {
            int ta = a; a = b; b = ta;
            int ti = i; i = j; j = ti;
        }
        return new Pair(a, b, Math.min(i, j), Math.max(i, j));
    }

    private static final Pattern P =
            Pattern.compile("\\s*(-?\\d+)\\s*,\\s*(-?\\d+)\\s*\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)\\s*");

    private Pair parsePairString(String s) {
        Matcher m = P.matcher(s);
        if (!m.matches()) {
            fail("Bad pair format: " + s + " (expected: 'a, b (i, j)')");
            return null; // unreachable
        }
        int a = Integer.parseInt(m.group(1));
        int b = Integer.parseInt(m.group(2));
        int i = Integer.parseInt(m.group(3));
        int j = Integer.parseInt(m.group(4));
        return pair(a, b, i, j);
    }

    private void assertPairsEqual(Set<Pair> expected, List<String> actualStrs) {
        assertNotNull(actualStrs, "Result list should not be null");
        Set<Pair> actual = new HashSet<>();
        for (String s : actualStrs) actual.add(parsePairString(s));
        assertEquals(expected, actual);
    }
}