package leetcode.alien_dictionary;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;

public class BFS {
    private int resIndex = 0;

    public String alienOrder(String[] words) {

        // Kahn's algorithm
        // the key of this solution is that,
        // start with the ones with in-degree = 0 in a queue and
        // remove it once adding it to the result
        // so part of its children will have in-degree = 0 and we put them into the queue,
        // the other children that have in-degree > 0 means it has other prerequites, and we'll just wait for their dependants get to result at first
        // after cleaning the queue, if the length of the result is less than the chars we have, that means some chars still have in-degree > 0
        // then there is a loop

        int count = 0; // total unique chars
        Map<Character, List<Character>> adjs = new HashMap<>();
        Map<Character, Integer> indegreeCounts = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            for (char c: words[i].toCharArray()) {
                if (! adjs.containsKey(c)) {
                    adjs.put(c, new ArrayList<>());
                    indegreeCounts.put(c, 0);
                    count++;
                }
            }
        }

        // build graph, also check whether any words are not in order
        if (! buildGraph(words, adjs, indegreeCounts)) return "";

        // add the ones which ingree is 0
        Queue<Character> queue = new LinkedList<>();
        for (char c: indegreeCounts.keySet()) {
            if (indegreeCounts.get(c) == 0)
                queue.add(c);
        }

        // BFS to add the chars into the res
        char[] res = new char[count];
        bfs(adjs, indegreeCounts, queue, res);

        if (resIndex != count) return "";

        return new String(res);
    }

    private boolean buildGraph(String[] words, Map<Character, List<Character>> adjs, Map<Character, Integer> indegreeCounts) {

        for (int i = 1; i < words.length; i++) {
            // check whether any words are not in order
            if (words[i].length() < words[i-1].length() && words[i-1].startsWith(words[i])) return false;
            int len = Math.min(words[i].length(), words[i-1].length());
            for (int j = 0; j < len; j++) {
                char a = words[i-1].charAt(j);
                char b = words[i].charAt(j);
                if (a != b) {
                    adjs.get(a).add(b);
                    indegreeCounts.put(b, indegreeCounts.get(b)+1);
                    break;
                }
            }
        }

        return true;
    }

    private void bfs(Map<Character, List<Character>> adjs, Map<Character, Integer> indegreeCounts, Queue<Character> queue, char[] res) {
        while (queue.peek() != null) {
            char c = queue.poll();
            res[resIndex++] = c;

            for (char a: adjs.get(c)) {
                indegreeCounts.put(a, indegreeCounts.get(a)-1);
                // only add the ones with in-degree = 0
                // because we know all the prerequites already in the result
                if (indegreeCounts.get(a) == 0) queue.add(a);
            }
        }
    }
}
