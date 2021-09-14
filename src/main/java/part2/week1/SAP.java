package part2.week1;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private static final int INFINITY = Integer.MAX_VALUE;
    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (new DirectedCycle(G).hasCycle()) throw new IllegalArgumentException();
        digraph = G;
    }

    private void validateVertex(int v) {
        if (digraph.outdegree(v) == 0) throw new IllegalArgumentException();
    }

    private void validateVertex(Iterable<Integer> v) {
        for (int i: v)
            validateVertex(i);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v); validateVertex(w);
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        return bfsV.distTo(w);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v); validateVertex(w);
        if (digraph.outdegree(v) == 0 || digraph.outdegree(w) == 0) throw new IllegalArgumentException();
        return 0;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertex(v); validateVertex(w);
        return 0;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertex(v); validateVertex(w);
        return 0;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in).reverse();
        SAP sap = new SAP(G);
        int l = sap.length(3, 5);
        if ( l == INFINITY)
            StdOut.println("---");
        else
            StdOut.println(l);
//        int s = Integer.parseInt(args[1]);
    }
}