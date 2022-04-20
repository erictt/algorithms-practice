package leetcode.graph_valid_tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {

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
        int seen = 0;

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{-1, 0});
        while (! queue.isEmpty()) {
            int[] node = queue.poll();
            if (visited[node[1]]) return false;
            visited[node[1]] = true;
            seen++;

            for (int j: graph[node[1]]) {
                if (node[0] == j) continue;
                queue.add(new int[]{node[1], j});
            }
        }

        return seen == n;
    }
}
