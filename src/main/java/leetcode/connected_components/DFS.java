package leetcode.connected_components;

import java.util.List;
import java.util.ArrayList;

public class DFS {
    public int countComponents(int n, int[][] edges) {

        List<Integer>[] graph = new List[n]; // List is faster than Set

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < edges.length; i++) {
            graph[edges[i][0]].add(edges[i][1]);
            graph[edges[i][1]].add(edges[i][0]);
        }

        boolean[] visited = new boolean[n];

        int count = 0;
        for (int i = 0; i < n; i++) {
            if (! visited[i]) {
                dfs(graph, visited, i);
                count++;
            }
        }
        return count;
    }

    private void dfs(List<Integer>[] graph, boolean[] visited, int i) {
        if (visited[i]) return;
        visited[i] = true; // it has to be executed before the for, otherwise it will create infinite loop!!!
        for (int j: graph[i]) {
            dfs(graph, visited, j);
        }
    }
}
