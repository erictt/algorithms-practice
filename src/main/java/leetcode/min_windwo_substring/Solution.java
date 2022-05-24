package leetcode.min_windwo_substring;

import java.util.LinkedList;

public class Solution {
    public String minWindow(String s, String t) {

        int lenT = t.length();
        int count = 0; // only used to measure whether we have covered all chars from t

        int[] amount = new int[128]; // use a map to calculate how many times each char showed up in t
        for (char c: t.toCharArray()) amount[c]++;
        int[] achieved = new int[128]; // used for measure how many valid chars we have reached

        LinkedList<Integer> validChars = new LinkedList<>();

        int start = -1, end = s.length();

        int minStart = start, minEnd = end;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (amount[c] == 0) continue;

            if (achieved[c] < amount[c]) count++;

            validChars.add(i);
            achieved[c]++;

            if (start == -1) start = i;

            if (count == lenT) {
                end = i;

                char c2 = s.charAt(start);
                while (achieved[c2] > amount[c2]) {
                    achieved[c2]--;
                    validChars.pop();
                    start = validChars.peek();
                    c2 = s.charAt(start);
                }
                if (end - start <= minEnd - minStart) {
                    minEnd = end;
                    minStart = start;
                }
            }
        }

        if (end == s.length()) return "";
        return s.substring(minStart, minEnd+1);
    }
}