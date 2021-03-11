package part1.week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int[][] blocks;
    private int zeroI;
    private int zeroJ;
    private final int dim;
    private final int manhattanCount;
    private final int hammingCount;

    public Board(int[][] initBlocks) {           // construct a board from an n-by-n array of blocks
        int countHam = 0;
        int countMan = 0;
        blocks = cloneBlocks(initBlocks);
        dim = blocks.length;
        for (int i = 0; i < initBlocks.length; i++) {
            for (int j = 0; j < initBlocks.length; j++) {
                if (initBlocks[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                } else {
                    if (initBlocks[i][j] != (i * initBlocks.length) + j + 1) {
                        countHam++;
                        countMan += manhattan(i, j);
                    }

                }
            }
        }
        manhattanCount = countMan;
        hammingCount = countHam;
    }

    public int dimension() {                // board dimension n
        return dim;
    }

    public int hamming() {                  // number of blocks out of place
        return hammingCount;
    }

    public int manhattan() {                 // sum of Manhattan distances between blocks and goal
        return manhattanCount;
    }

    private int manhattan(int i, int j) {
        return Math.abs(i - (blocks[i][j] - 1) / dimension()) + Math.abs(j - (blocks[i][j] - 1) % dimension());
    }

    public boolean isGoal() {               // is this board the goal board?
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] == 0) continue;
                if (blocks[i][j] != (i * blocks.length) + j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public Board twin() {                    // a board that is obtained by exchanging any pair of blocks
        if (dimension() <= 1) return null;
        int[][] cloned = cloneBlocks(blocks);
        Board temp = new Board(cloned);
        if (cloned[0][0] != 0 && cloned[0][1] != 0)
            temp.swapPositions(0, 0, 0, 1);
        else
            temp.swapPositions(1, 0, 1, 1);
        return temp;
    }

    private Board newNeighbor(int i2, int j2) {
        int[][] cloned = cloneBlocks(blocks);
        int temp = cloned[zeroI][zeroJ];
        cloned[zeroI][zeroJ] = cloned[i2][j2];
        cloned[i2][j2] = temp;
        return new Board(cloned);
    }

    private int[][] cloneBlocks(int[][] bs) {
        int[][] cloned = new int[bs.length][bs.length];
        for (int i = 0; i < bs.length; i++) {
            for (int j = 0; j < bs.length; j++) {
                cloned[i][j] = bs[i][j];

            }
        }
        return cloned;
    }

    private void swapPositions(int i1, int j1, int i2, int j2) {
        int temp = this.blocks[i1][j1];
        this.blocks[i1][j1] = this.blocks[i2][j2];
        this.blocks[i2][j2] = temp;
    }

    public boolean equals(Object y) {       // does this board equal y?
        if (this == y) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return this.dimension() == that.dimension() && Arrays.deepEquals(this.blocks, that.blocks);
    }

    public Iterable<Board> neighbors() {     // all neighboring boards
        ArrayList<Board> boards = new ArrayList<>();

        if (this.zeroI - 1 >= 0) {
            boards.add(this.newNeighbor(this.zeroI - 1, this.zeroJ));
        }
        if (this.zeroI + 1 < this.dimension()) {
            boards.add(this.newNeighbor(this.zeroI + 1, this.zeroJ));
        }
        if (this.zeroJ - 1 >= 0) {
            boards.add(this.newNeighbor(this.zeroI, this.zeroJ - 1));
        }
        if (this.zeroJ + 1 < this.dimension()) {
            boards.add(this.newNeighbor(this.zeroI, this.zeroJ + 1));
        }

        return boards;
    }

    public String toString() {               // string representation of this board (in the output format specified below)
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        sb.append(dim).append("\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                sb.append(String.format("%2d ", this.blocks[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) { // unit tests (not graded)
        In in = new In(args[0]);
        int n = in.readInt();
        StdOut.println(n);
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();

        Board initial = new Board(blocks);
        StdOut.println("Initial Board: ");
        StdOut.println(initial.toString());
        StdOut.println("Manhattan: " + initial.manhattan());
        StdOut.println("Hamming: " + initial.hamming());

//        StdOut.println("All Neighbors: ");
//        Iterable<Board> boards = initial.neighbors();
//        for (Board board : boards) {
//            StdOut.println(board.manhattan());
//            StdOut.println(board);
//        }
    }
}