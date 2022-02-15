package part2.week1;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;

public class SAP {

    private static final int INFINITY = Integer.MAX_VALUE;
    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        // this will make the diagraph immutable
        digraph = new Digraph(G);
        // TODO miss checking if there is a cycle
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= digraph.V()) throw new IllegalArgumentException();
    }

    private void validateVertex(Iterable<Integer> v) {
        if (v == null) throw new IllegalArgumentException();
        for (Integer i: v) {
            if (i == null) throw new IllegalArgumentException();
            validateVertex(i);
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        ArrayList<Integer> ws = new ArrayList<>();
        ArrayList<Integer> vs = new ArrayList<>();
        ws.add(w);
        vs.add(v);
        int length = bfs(ws, vs)[1];
        return length == INFINITY ? -1 : length;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        ArrayList<Integer> ws = new ArrayList<>();
        ArrayList<Integer> vs = new ArrayList<>();
        ws.add(w);
        vs.add(v);
        int ancestor = bfs(ws, vs)[0];
        return ancestor == INFINITY ? -1 : ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertex(v);
        validateVertex(w);
        int distance = bfs(w, v)[1];
        return distance == INFINITY ? -1 : distance;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertex(v);
        validateVertex(w);
        int ancestor = bfs(w, v)[0];
        return ancestor == INFINITY ? -1 : ancestor;
    }

    // method 1: loop both sources at the same time
    private int[] bfs(Iterable<Integer> sourcesW, Iterable<Integer> sourcesV) {
        boolean[] markedW = new boolean[digraph.V()];
        boolean[] markedV = new boolean[digraph.V()];
        int[] distToW = new int[digraph.V()];
        int[] distToV = new int[digraph.V()];

        Queue<Integer> qW = new Queue<>();
        Queue<Integer> qV = new Queue<>();
        for (int s : sourcesW) {
            markedW[s] = true;
            distToW[s] = 0;
            qW.enqueue(s);
        }
        for (int s : sourcesV) {
            if (markedV[s]) return new int[]{s, 0};
            markedV[s] = true;
            distToV[s] = 0;
            qV.enqueue(s);
        }
        boolean flag = false;
        int[] candidate = {-1, -1};
        while (!qW.isEmpty() || !qV.isEmpty()) {
            Queue<Integer> q = null;
            boolean[] marked = null;
            int[] distTo = null;
            if ((!flag && !qW.isEmpty()) || qV.isEmpty()) {
                flag = true;
                q = qW;
                marked = markedW;
                distTo = distToW;
            } else if (!qV.isEmpty()) {
                flag = false;
                q = qV;
                marked = markedV;
                distTo = distToV;
            }
            if (q == null || q.isEmpty()) {
                return new int[]{-1, -1};
            }
            int s = q.dequeue();
            if (markedW[s] && markedV[s]) {
                if (candidate[1] == -1 || candidate[1] > distToV[s] + distToW[s]) {
                    candidate = new int[]{s, distToV[s] + distToW[s]};
                }
            }
            for (int i : digraph.adj(s)) {
                if (!marked[i]) {
                    distTo[i] = distTo[s] + 1;
                    marked[i] = true;
                    q.enqueue(i);
                }
            }
        }
        return candidate;
    }

    // method 2: use BreadthFirstDirectedPaths to mark one source, and then loop the other one
//    private int[] bfs(Iterable<Integer> sourcesW, Iterable<Integer> sourcesV) {
//
//        int[] candidate = {-1, -1};
//        if (!sourcesV.iterator().hasNext() || !sourcesW.iterator().hasNext()) {
//            return candidate;
//        }
//        BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(this.digraph, sourcesW);
//        boolean[] markedV = new boolean[digraph.V()];
//        int[] distToV = new int[digraph.V()];
//
//        Queue<Integer> qV = new Queue<>();
//
//        for (int s : sourcesV) {
//            // same concern as in the loop.
//            // one result doesn't mean it's the shortest one.
//            if (pathW.hasPathTo(s)) candidate = new int[]{s, pathW.distTo(s)};
//            markedV[s] = true;
//            distToV[s] = 0;
//            qV.enqueue(s);
//        }
//
//        while (!qV.isEmpty()) {
//            int s = qV.dequeue();
//            // don't return when finding the first one, due to certain situations
//            // e.g. 1 0, 1 2, 2 3, 3 4, 4 5, 5 0 and check 3 and 1 (digraph2.txt)
//            // it's possible we get distance 4 at first, but the shortest is 2
//            if (pathW.hasPathTo(s) && markedV[s]) {
//                if (candidate[1] == -1 || candidate[1] > (distToV[s] + pathW.distTo(s))) {
//                    candidate = new int[]{s, distToV[s] + pathW.distTo(s)};
//                }
//            }
//            for (int i : digraph.adj(s)) {
//                if (!markedV[i]) {
//                    distToV[i] = distToV[s] + 1;
//                    markedV[i] = true;
//                    qV.enqueue(i);
//                }
//            }
//        }
//        return candidate;
//    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}