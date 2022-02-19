package part2.week3;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class BaseballElimination2 {

    private final static int FLOW_SOURCE_INDEX = 0;
    private final static int FLOW_TARGET_INDEX = 1;

    private final int n;
    private final int len;
    private boolean[] marked;
    private final HashMap<String, Integer> teamMap = new HashMap<>();
    private final List<Team> teams = new ArrayList<>();
    private final FlowNetwork fn;
    // use the upper triangle in the matrix to store the capacity and the lower triangle for flow
    private final int[][] matches;

    private FlowEdge[] edgeTo;

    private final HashMap<String, ArrayList<String>> eliminatedTeams = new HashMap<>();

    // create a baseball division from given filename in format specified below
    public BaseballElimination2(String filename) {
        In in = new In(filename);

        n = in.readInt();
        if (n <= 0) throw new IllegalArgumentException("empty file or file doesn't exist");

        // 0: source
        // 1: target
        // 2 - N+1: team
        // N+2 -> N(N+1)/2 + 1: match
        len = (n*n+n)/2+2;
        fn = new FlowNetwork(len);
        matches = new int[n][n];

        try {
            int matchIndex = n+2;
            int column = 0;
            for (int row = 0; row < n; row++) {
                String teamName = in.readString();
                teams.add(new Team(teamName, in.readInt(), in.readInt(), in.readInt()));
                teamMap.put(teamName, row);
                eliminatedTeams.put(teamName, new ArrayList<String>());
                for (int k = column+4; k < n+4; k++) {
                    int left = in.readInt();
                    if (k - 4 <= row) continue;
                    matches[row][k-4] = left;
                    fn.addEdge(new FlowEdge(FLOW_SOURCE_INDEX, matchIndex, left));
                    fn.addEdge(new FlowEdge(matchIndex, row+2, Integer.MAX_VALUE));
                    fn.addEdge(new FlowEdge(matchIndex, k-4+2, Integer.MAX_VALUE));
                    matchIndex++;
                }
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Digraph constructor", e);
        }
    }

    private class Team {
        String name;
        int win;
        int loss;
        int left;

        Team(String name, int win, int loss, int left) {
            this.name = name;
            this.win = win;
            this.loss = loss;
            this.left = left;
        }
    }

    // number of teams
    public int numberOfTeams() {
        return n;
    }

    // all teams
    public Iterable<String> teams() {
        return teamMap.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        if (!teamMap.containsKey(team)) throw new IllegalArgumentException();
        return teams.get(teamMap.get(team)).win;
    }

    // number of losses for given team
    public int losses(String team) {
        if (!teamMap.containsKey(team)) throw new IllegalArgumentException();
        return teams.get(teamMap.get(team)).loss;
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (!teamMap.containsKey(team)) throw new IllegalArgumentException();
        return teams.get(teamMap.get(team)).left;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (!teamMap.containsKey(team1) || !teamMap.containsKey(team2))
            throw new IllegalArgumentException();
        int a = teamMap.get(team1);
        int b = teamMap.get(team2);
        return a > b ? matches[a][b] : matches[b][a];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (!teamMap.containsKey(team)) throw new IllegalArgumentException();
        int x = teamMap.get(team);
        Team teamX = teams.get(x);

        if (eliminatedTeams.get(team).size() > 0) return true;

        FlowNetwork flowNetwork = new FlowNetwork(len);
        for (FlowEdge e: fn.edges()) {
            flowNetwork.addEdge(new FlowEdge(e));
        }

        // trivial elimination
        // also initial the capacity for each team
        for (int i = 0; i < teams.size(); i++) {
            Team teamI = teams.get(i);
            if (teamX.win + teamX.left < teamI.win)
                eliminatedTeams.get(teamX.name).add(teamI.name);
            else
                flowNetwork.addEdge(new FlowEdge(i+2, FLOW_TARGET_INDEX, teamX.win + teamX.left - teamI.win));
        }
        if (eliminatedTeams.get(team).size() > 0) return true;

        // nontrivial elimination
        while (hasAugmentingPath(flowNetwork)) {
            // calculate the bottleneck amount
            int index = FLOW_TARGET_INDEX;
            double min = Double.POSITIVE_INFINITY;
//            String path = index + " -> ";
            while (index != FLOW_SOURCE_INDEX) {
                double flow = edgeTo[index].residualCapacityTo(index);
                if (flow < min) min = flow;
//                path += edgeTo[index].other(index) + " -> ";
                index = edgeTo[index].other(index);
            }
//            System.out.println(path + ". min: " + min);
            index = FLOW_TARGET_INDEX;
            while (index != FLOW_SOURCE_INDEX) {
                edgeTo[index].addResidualFlowTo(index, min);
                index = edgeTo[index].other(index);
            }
        }

        // search for min cut
//        boolean[] marked = new boolean[len];
//        Queue<Integer> queue = new Queue<>();
//        queue.enqueue(FLOW_SOURCE_INDEX);
//        marked[FLOW_SOURCE_INDEX] = true;
//        while (!queue.isEmpty()) {
//            int i = queue.dequeue();
//            for (FlowEdge a: flowNetwork.adj(i)) {
//                int j = a.other(i);
//                if (marked[j]) continue;
//                if (j == FLOW_TARGET_INDEX && a.flow() == a.capacity()) {
//                    eliminatedTeams.get(team).add(teams.get(i-2).name);
//                    marked[i] = true;
//                    continue;
//                }
//                if (a.flow() < a.capacity()) {
//                    queue.enqueue(j);
//                    marked[j] = true;
//                }
//            }
//        }
        for (int v = 2; v < n+2; v++) {
            if (marked[v])
                eliminatedTeams.get(team).add(teams.get(v-2).name);
        }

        if (eliminatedTeams.get(team).size() != 0)
            return true;
        return false;
    }

    private boolean hasAugmentingPath(FlowNetwork flowNetwork) {
        marked = new boolean[len]; // the last mark will be used for checking min-cut
        edgeTo = new FlowEdge[len];

        Queue<Integer> queue = new Queue<>();
        queue.enqueue(FLOW_SOURCE_INDEX);
        marked[FLOW_SOURCE_INDEX] = true;
        while (!queue.isEmpty() && !marked[FLOW_TARGET_INDEX]) {
            int i = queue.dequeue();
            for (FlowEdge a: flowNetwork.adj(i)) {
                int j = a.other(i);
                if (marked[j]) continue;
                if (a.residualCapacityTo(j) > 0) {
                    edgeTo[j] = a;
                    marked[j] = true;
                    queue.enqueue(j);
                }
            }
        }

        return marked[FLOW_TARGET_INDEX];
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return eliminatedTeams.get(team);
    }

    public static void main(String[] args) {
        BaseballElimination2 division = new BaseballElimination2(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
