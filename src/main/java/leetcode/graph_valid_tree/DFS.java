package leetcode.graph_valid_tree;

import java.util.ArrayList;
import java.util.List;

class DFS {

    private int seen = 0;

    public boolean validTree(int n, int[][] edges) {

        List<Integer>[] graph = new List[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < edges.length; i++) {
            graph[edges[i][0]].add(edges[i][1]);
            graph[edges[i][1]].add(edges[i][0]);
        }

        boolean[] visited = new boolean[n];
        if (dfs(graph, visited, -1, 0)) return false;
        System.out.println(seen);
        return seen == n;
    }

    // return true if there is a cycle
    private boolean dfs(List<Integer>[] graph, boolean[] visited, int parent, int i) {
        // alternatively, we can use a Set to store all visited node. then won't need int seen and boolean[] visited.
        if (visited[i]) return true;
        visited[i] = true; // notice this line has to be above of the for loop to avoid StackOverFlow
        seen++;
        for (int j: graph[i]) {
            if (parent == j) continue;
            if (dfs(graph, visited, i, j)) return true;
        }

        return false;
    }
}