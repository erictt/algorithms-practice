package leetcode.alien_dictionary;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// https://leetcode.com/problems/alien-dictionary/
public class DFS {
    private static final int N = 26;

    private int resIndex = 0;

    public String alienOrder(String[] words) {

        // build graph
        int count = 0;
        Map<Character, Set<Character>> adjs = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            for (char c: words[i].toCharArray()) {
                if (! adjs.containsKey(c)) {
                    adjs.put(c, new HashSet<>());
                    count++;
                }
            }
        }

        // used to store the result
        char[] res = new char[count];
        resIndex = count - 1;

        // build the graph
        boolean built = buildGraph(words, adjs);
        if (! built) return "";

        // topological sort
        boolean[] inRes = new boolean[N]; // whether the char is already in our result
        boolean[] marked = new boolean[N]; // used for marking whether the char was visited in current DFS
        for (char c: adjs.keySet()) {
            if (! inRes[c-'a']) {
                boolean hasCycle = dfs(adjs, c, res, inRes, marked);
                if (hasCycle) return "";
            }
        }

        return new String(res);
    }

    // if the words[i] is shorter than words[i-1] and words[i-1] started with words[i],
    // then it's disorder graph, return false;
    //
    private boolean buildGraph(String[] words, Map<Character, Set<Character>> adjs) {

        for (int i = 1; i < words.length; i++) {
            int len = Math.min(words[i].length(), words[i-1].length());
            int countSame = 0;
            for (int j = 0; j < len; j++) {
                char a = words[i-1].charAt(j), b = words[i].charAt(j);
                if (a != b) {
                    adjs.get(a).add(b);
                    break;
                } else countSame++;
            }
            // this is to avoid the error of cases like : [abcd, ab]. ab is supposed to be before abcd
            if (countSame == len && words[i].length() < words[i-1].length()) return false;
        }
        return true;
    }

    // return true iff there is a cycle
    private boolean dfs(Map<Character, Set<Character>> adjs, char c, char[] res, boolean[] inRes, boolean[] marked) {

        int index = c-'a';

        if (inRes[index]) return false;

        if (marked[index]) return true; // there is a cycle
        marked[index] = true;
        for (char a: adjs.get(c)) {
            if (dfs(adjs, a, res, inRes, marked)) return true;
        }
        marked[index] = false;

        res[resIndex--] = c;
        inRes[index] = true;

        return false;
    }
}
