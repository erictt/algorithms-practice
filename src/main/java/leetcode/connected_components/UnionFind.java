package leetcode.connected_components;

public class UnionFind {
    public int countComponents(int n, int[][] edges) {

        int[] parent = new int[n];
        int[] size = new int[n];
        for (int i = 0; i < n; i++) { // V
            parent[i] = i;
            size[i] = 1;
        }

        int count = n;

        for (int i = 0; i < edges.length; i++) { // E
            int root0 = root(parent, edges[i][0]); // logV
            int root1 = root(parent, edges[i][1]); // logV
            if (connected(root0, root1)) continue;
            union(parent, size, root0, root1);
            count--;
        }

        return count;
    }

    // i made a mistake last time here,
    // you don't need to initialize a variable like int root = parent[i] at first.
    private int root(int[] parent, int i) { // time complexity: logN because the depth of the tree is logN at most.
        while (i != parent[i]) {
            i = parent[i];
        }
        return i;
    }

    private boolean connected(int root0, int root1) {
        return root0 == root1;
    }

    private void union(int[] parent, int[] size, int root0, int root1) {
        if (size[root0] > size[root1]) {
            parent[root1] = root0;
            size[root0] += size[root1];
        } else {
            parent[root0] = root1;
            size[root1] += size[root0];
        }
    }
}
