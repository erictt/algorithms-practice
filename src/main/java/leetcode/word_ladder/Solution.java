package leetcode.word_ladder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        Set<String> dicts = new HashSet<>();

        boolean hasEndWord = false;
        for (String word: wordList) {
            if (word.equals(beginWord)) continue;
            if (word.equals(endWord)) hasEndWord = true;
            dicts.add(word);
        }
        if (! hasEndWord) return 0;

        // build graph with steps,
        // steps 0 will be only beginWord
        List<Set<String>> list = new ArrayList<>();
        int steps = 0;
        list.add(steps, new HashSet<String>());
        list.get(steps).add(beginWord);

        // if list.get(step) is empty, there is no path to continue to build the graph.
        while (! dicts.isEmpty() && list.get(steps).size() > 0) {

            list.add(steps+1, new HashSet<String>());

            for (String str: list.get(steps)) {
                addAdjToNextStep(str, dicts, list.get(steps+1));
                if (! dicts.contains(endWord)) return steps+2;
            }
            steps++;
        }

        return 0;
    }

    // change the char in str one at a time and check whether dicts has it
    // if it does, move it to nextStep's Set
    private void addAdjToNextStep(String str, Set<String> dicts, Set<String> newStep) {

        for (int i = 0; i < str.length(); i++) {
            char[] chars = str.toCharArray();
            for (char c = 'a'; c <= 'z'; c++) {
                chars[i] = c;
                String newStr = new String(chars);
                if (! newStr.equals(str) && dicts.contains(newStr)) {
                    dicts.remove(newStr);
                    newStep.add(newStr);
                    // break; // can't use break here, cause it's possible there are more than one match.
                }
            }
        }
    }

}
