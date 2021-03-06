package week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.LinkedList;

public class Solver {
    private int moves = -1;
    private boolean solvable;
    private LinkedList<Board> finalSolution;

    private class SearchNode {
        private final Board board;
        private final SearchNode parent;
        private int moves;

        private SearchNode(Board board, SearchNode parent) {
            this.board = board;
            this.parent = parent;
            if (this.parent == null)
                this.moves = 0;
            else
                this.moves = this.parent.moves + 1;
        }
    }

    public Solver(Board initial) {           // find a solution to the initial board (using the A* algorithm)
        MinPQ<SearchNode> boardPQ = new MinPQ<>(new BoardOrder());
        MinPQ<SearchNode> twinBoardPQ = new MinPQ<>(new BoardOrder());

        SearchNode searchNode = new SearchNode(initial, null);
        SearchNode searchNodeTwin = new SearchNode(initial.twin(), null);
        while (!searchNode.board.isGoal() && !searchNodeTwin.board.isGoal()) {

            for (Board neighbor : searchNode.board.neighbors()) {
                if (searchNode.parent == null || !searchNode.parent.board.equals(neighbor)) {
                    boardPQ.insert(new SearchNode(neighbor, searchNode));
                }
            }
            for (Board neighbor : searchNodeTwin.board.neighbors()) {
                if (searchNodeTwin.parent == null || !searchNodeTwin.parent.board.equals(neighbor)) {
                    twinBoardPQ.insert(new SearchNode(neighbor, searchNodeTwin));
                }
            }

            searchNode = boardPQ.delMin();
            searchNodeTwin = twinBoardPQ.delMin();
        }

        if (searchNode.board.isGoal()) {
            this.solvable = true;
            this.buildSolution(searchNode);
        } else {
            this.solvable = false;
        }
    }

    private static class BoardOrder implements Comparator<SearchNode> {
        public int compare(SearchNode v, SearchNode w) {
            if (v.board.manhattan() + v.moves < w.board.manhattan() + w.moves) return -1;
            if (v.board.manhattan() + v.moves > w.board.manhattan() + w.moves) return +1;
            return 0;
        }
    }

    public boolean isSolvable() {            // is the initial board solvable?
        return this.solvable;
    }

    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        return this.moves;
    }

    public Iterable<Board> solution() {      // sequence of boards in a shortest solution; null if unsolvable
        return this.finalSolution;
    }

    private void buildSolution(SearchNode searchNode) {
        LinkedList<Board> solution = new LinkedList<>();
        solution.addFirst(searchNode.board);
        this.moves = 0;
        while (searchNode.parent != null) {
            solution.addFirst(searchNode.parent.board);
            searchNode = searchNode.parent;
            this.moves++;
        }
        this.finalSolution = solution;
    }

    public static void main(String[] args) { // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}