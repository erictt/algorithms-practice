package leetcode.course_schedule;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/course-schedule/solution/
public class PostOrder {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        return ! new Graph(numCourses, prerequisites).hasCycle();
    }

    private class Graph {

        List<Integer>[] vertices;
        int count;

        Graph(int count, int[][] edges) {
            this.count = count;
            vertices = new List[count];

            for (int[] edge: edges) {
                if (vertices[edge[1]] == null) vertices[edge[1]] = new ArrayList<>();
                vertices[edge[1]].add(edge[0]);
            }
        }

        boolean hasCycle() {

            boolean[] marked = new boolean[count]; // used to mark visited vertices
            for (int i = 0; i < count; i++) {
                if (marked[i] || vertices[i] == null) continue;
                boolean[] onStack = new boolean[count]; // used to mark visited vertices in current dfs
                if (dfs(i, marked, onStack)) return true;
            }

            return false;
        }

        boolean dfs(int i, boolean[] marked, boolean[] onStack) {
            if (vertices[i] == null) return false;
            if (onStack[i] == true) return true;
            onStack[i] = true;
            for (int j: vertices[i]) {
                if (marked[j]) continue;
                if (dfs(j, marked, onStack)) return true;
            }
            // I was confused why needed to set false,
            // the reason is we might revisit the node in current recursion,
            // and if it's marked as true, then it will be treated as a loop
            // e.g. 1 -> 2 -> 3; 1 -> 3. we visit 3 twice but there is no loop!!
            onStack[i] = false;
            marked[i] = true;
            return false;
        }
    }
}
