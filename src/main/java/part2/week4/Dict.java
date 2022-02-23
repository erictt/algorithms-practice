package part2.week4;

import java.util.HashMap;

public class Dict {

    private static final int R = 26;

    private final Node root;      // root of trie

    private final HashMap<String, Integer> scores;

    public Dict() {
        root = new Node(0);
        scores = new HashMap<>();
    }

    public static class Node {
        private final int length;
        private String word;
        private Node[] next = new Node[R];

        Node(int len) {
            length = len;
        }

        int getScore() {
            int score = 0;
            if (word != null) {
                if (length == 3 || length == 4) score = 1;
                else if (length == 5) score = 2;
                else if (length == 6) score = 3;
                else if (length == 7) score = 5;
                else if (length >= 8) score = 11;
            }
            return score;
        }
    }

    public void addWord(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {

            if (node.next[word.charAt(i) - 'A'] == null) {
                node.next[word.charAt(i) - 'A'] = new Node(node.length+1);
            }

            if (i == word.length() - 1) {
                node.next[word.charAt(i) - 'A'].word = word;
                scores.put(word, node.next[word.charAt(i) - 'A'].getScore());
            }
            node = node.next[word.charAt(i) - 'A'];
        }
    }

    public int getScore(String word) {
        return scores.containsKey(word) ? scores.get(word) : 0;
    }

    public int getScore(Node node) {
        return scores.get(getWord(node));
    }

    public Node[] getNext(Node node) {
        if (node == null) return root.next;
        return node.next;
    }

    public boolean isWord(Node node) {
        return node.word != null;
    }

    public String getWord(Node node) {
        return node.word;
    }
}
