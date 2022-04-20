package leetcode.graph_valid_tree;

import java.util.ArrayList;
import java.util.List;

public class AdvancedDFS {

    // Problem: https://leetcode.com/problems/graph-valid-tree/

    /*
     * from https://leetcode.com/problems/graph-valid-tree/solution/
     * For the graph to be a valid tree, it must have exactly n - 1 edges. Any less, and it can't possibly be fully connected.
     * Any more, and it has to contain cycles. Additionally,
     * if the graph is fully connected and contains exactly n - 1 edges, it can't possibly contain a cycle, and therefore must be a tree!
     *
     * These facts are fairly straightforward to prove.
     * We won't go into why they are true here, but if you're not familiar with these facts, then we recommend reading up on graph theory.
     * It is very important to be confident with graph theory in-order to pass the interviews at a top tech company.
     *
     * Going by this definition, our algorithm needs to do the following:
     * Check whether or not there are n - 1 edges. If there's not, then return false.
     * Check whether or not the graph is fully connected. Return true if it is, false if otherwise.
     */
    private int seen = 0;

    public boolean validTree(int n, int[][] edges) {

        if (n != edges.length + 1) return false;

        List<Integer>[] graph = new List[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < edges.length; i++) {
            graph[edges[i][0]].add(edges[i][1]);
            graph[edges[i][1]].add(edges[i][0]);
        }

        boolean[] visited = new boolean[n];

        dfs(graph, visited, 0);

        /*
         * alternatively, use stack to iterate DFS or use queue to do BFS
         * Stack<Integer> stack = new Stack<>();
         * stack.push(0);
         *
         * int seen = 0;
         * while (! stack.empty()) {
         *     int i = stack.pop();
         *     if (visited[i]) continue;
         *     visited[i] = true;
         *     seen++;
         *     for (int j: graph[i]) stack.push(j);
         * }
         */

        return seen == n;
    }

    private void dfs(List<Integer>[] graph, boolean[] visited, int i) {
        if (visited[i]) return;
        visited[i] = true;
        seen++;
        for (int j: graph[i]) dfs(graph, visited, j);
    }

    /*
     * Time Complexity : O(N)
     * If E != N - 1, then return false;
     * Otherwise, loop N+E to build the graph, because N = E + 1, So it's O(N+N) = O(N)
     * Then the DFS/BFS to check every edges in the graph, which is N or E as well
     * So in total, it's O(N).
     *
     * Space Complexity : O(N)
     * graph took O(E+N) and in the worse case, the stack/queue will take N as well
     * So in total it's O(N).
     */
}
