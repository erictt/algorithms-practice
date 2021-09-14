package part2.week1;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Corner cases.  Throw an IllegalArgumentException in the following situations:
 *   - Any argument to the constructor or an instance method is null
 *   - The input to the constructor does not correspond to a rooted DAG.
 *   - Any of the noun arguments in distance() or sap() is not a WordNet noun.
 */
public class WordNet {

    private final HashMap<String, Integer> wordMap = new HashMap<>();
    private final HashMap<Integer, String> idMap = new HashMap<>();
    private final Digraph digraph;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();

        In in = new In(synsets);
        while(in.hasNextLine()) {
            String str = in.readLine();
            String[] strArr = str.split(",");
            if (strArr.length < 3) throw new NoSuchElementException();
            wordMap.put(strArr[1], Integer.getInteger(strArr[0]));
            idMap.put(Integer.getInteger(strArr[0]), strArr[1]);
        }

        digraph = new Digraph(wordMap.size());
        in = new In(hypernyms);
        while(in.hasNextLine()) {
            String str = in.readLine();
            String[] strArr = str.split(",");
            if (strArr.length < 2) throw new NoSuchElementException();
            for (int i = 1; i < strArr.length; i++) {
                digraph.addEdge(Integer.getInteger(strArr[0]), Integer.getInteger(strArr[i]));
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return wordMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        return 0; // TODO
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        return ""; // TODO
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}