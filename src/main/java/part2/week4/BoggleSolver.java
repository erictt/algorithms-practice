package part2.week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Set;

public class BoggleSolver
{
    private final Dict dict;
    // used for recording the position of each word
    // when reaching the length, there is a match
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        dict = new Dict();

        for (String str: dictionary) {
            this.dict.addWord(str);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> result = new HashSet<>();

        Matrix matrix = new Matrix(board);
        Dict.Node[] next = dict.getNext(null);

        boolean[][] marked = new boolean[board.rows()][board.cols()];

        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                Position x = matrix.positions[i][j];
                if (next[x.id] != null) {
                    dfs(x, marked, next[x.id], result);
                }
            }
        }
        return result;
    }

    private void dfs(Position x, boolean[][] marked, Dict.Node node, Set<String> result) {
        if (node == null)  return;

        // if current position is Q, check if the children has U.
        // move to U if exists
        if (x.id == 'Q'-'A') {
            if (dict.getNext(node)['U'-'A'] != null) {
                node = dict.getNext(node)['U'-'A'];
            } else { // the dict doesn't U after Q
                return;
            }
        }

        marked[x.i][x.j] = true;

        if (dict.isWord(node) && dict.getScore(node) > 0) {
            result.add(dict.getWord(node));
        }

        for (Position p: x.adj) {
            if (marked[p.i][p.j]) continue;
            dfs(p, marked, dict.getNext(node)[p.id], result);
        }

        marked[x.i][x.j] = false;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        return dict.getScore(word);
    }

    private static class Matrix {

        Position[][] positions;
        BoggleBoard board;

        Matrix(BoggleBoard board) {
            this.board = board;
            positions = new Position[board.rows()][board.cols()];
            for (int i = 0; i < board.rows(); i++) {
                for (int j = 0; j < board.cols(); j++) {
                    positions[i][j] = new Position(i, j, board.getLetter(i, j));
                }
            }
            for (int i = 0; i < board.rows(); i++) {
                for (int j = 0; j < board.cols(); j++) {
                    positions[i][j].setAdj(positions);
                }
            }
        }
    }

    private static class Position {
        int i;
        int j;
        int id;
        Set<Position> adj;

        Position(int i, int j, char letter) {
            this.i = i;
            this.j = j;
            this.id = letter - 'A';
        }

        void setAdj(Position[][] positions) {
            int rows = positions.length;
            int cols = positions[0].length;
            adj = new HashSet<>();
            // upper three
            if (i > 0) {
                if (j > 0) positions[i][j].adj.add(positions[i-1][j-1]);
                positions[i][j].adj.add(positions[i-1][j]);
                if (j + 1 < cols) positions[i][j].adj.add(positions[i-1][j+1]);
            }

            // bottom three
            if (i + 1 < rows) {
                if (j > 0) positions[i][j].adj.add(positions[i+1][j-1]);
                positions[i][j].adj.add(positions[i+1][j]);
                if (j + 1 < cols) positions[i][j].adj.add(positions[i+1][j+1]);
            }

            // left
            if (j > 0) positions[i][j].adj.add(positions[i][j-1]);

            // right
            if (j + 1 < cols) positions[i][j].adj.add(positions[i][j+1]);
        }

    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
