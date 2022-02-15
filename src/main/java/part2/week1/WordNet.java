package part2.week1;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;


/**
 * Corner cases.  Throw an IllegalArgumentException in the following situations:
 *   - Any argument to the constructor or an instance method is null
 *   - The input to the constructor does not correspond to a rooted DAG.
 *   - Any of the noun arguments in distance() or sap() is not a WordNet noun.
 */
public class WordNet {

    private final ArrayList<String> synsets = new ArrayList<>();
    private final HashMap<String, ArrayList<Integer>> synsetNounMap = new HashMap<>();
    private final SAP s;

    // constructor takes the name of the two input files
    public WordNet(String synsetFile, String hypernymFile) {

        if (synsetFile == null || hypernymFile == null) throw new IllegalArgumentException();

        In in = new In(synsetFile);
        while (in.hasNextLine()) {
            String str = in.readLine();
            String[] strArr = str.split(",");
            if (strArr.length < 3) throw new NoSuchElementException();
            synsets.add(strArr[1]);
            String[] nounArr = strArr[1].split(" ");
            for (String nStr: nounArr) {
                synsetNounMap.putIfAbsent(nStr, new ArrayList<>());
                synsetNounMap.get(nStr).add(Integer.parseInt(strArr[0]));
            }
        }

        Digraph digraph = new Digraph(synsets.size());
        in = new In(hypernymFile);
        while (in.hasNextLine()) {
            String str = in.readLine();
            String[] strArr = str.split(",");
            if (strArr.length < 1) throw new NoSuchElementException();
            for (int i = 1; i < strArr.length; i++) {
                digraph.addEdge(Integer.parseInt(strArr[0]), Integer.parseInt(strArr[i]));
            }
        }
        s = new SAP(digraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetNounMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return synsetNounMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        return s.length(findIds(nounA), findIds(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        return synsets.get(s.ancestor(findIds(nounA), findIds(nounB)));
    }

    private Iterable<Integer> findIds(String noun) {
        return synsetNounMap.get(noun);
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        while (!StdIn.isEmpty()) {
            String v = StdIn.readString();
            String w = StdIn.readString();
            int length   = wordnet.distance(v, w);
            String ancestor = wordnet.sap(v, w);
            StdOut.printf("length = %d, ancestor = %s\n", length, ancestor);
        }
    }
}