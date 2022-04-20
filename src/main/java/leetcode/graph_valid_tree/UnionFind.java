package leetcode.graph_valid_tree;

public class UnionFind {

    public boolean validTree(int n, int[][] edges) {

        if (n != edges.length + 1) return false;

        int[] roots = new int[n];
        int[] sizes = new int[n];

        for (int i = 0; i < n; i++) {
            roots[i] = i;
            sizes[i] = 1;
        }

        for (int i = 0; i < edges.length; i++) {
            if (connected(roots, edges[i][0], edges[i][1])) return false;
            union(roots, sizes, edges[i][0], edges[i][1]);
        }

        return true;
    }

    private boolean connected(int[] roots, int i, int j) {
        return root(roots, i) == root(roots, j);
    }

    private int root(int[] roots, int i) {
        while (i != roots[i]) {
            i = roots[roots[i]];
        }
        return i;
    }

    private void union(int[] roots, int[] sizes, int i, int j) {
        int root1 = root(roots, i);
        int root2 = root(roots, j);
        if (sizes[root1] > sizes[root2]) {
            roots[root2] = root1;
            sizes[root1] += sizes[root2];
        } else {
            roots[root1] = root2;
            sizes[root2] += sizes[root1];
        }
    }
}