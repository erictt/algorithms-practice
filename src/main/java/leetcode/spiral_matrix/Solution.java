package leetcode.spiral_matrix;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    private final static int LEFT = 0;
    private final static int RIGHT = 1;
    private final static int UP = 2;
    private final static int DOWN = 3;

    public List<Integer> spiralOrder(int[][] matrix) {

        // up's default is 0 !!
        int[] limits = new int[]{-1, matrix[0].length, 0, matrix.length};

        List<Integer> result = new ArrayList<>();

        // start with 0, -1 to make sure the first step is valid
        // think about the corner case like [[1],[2]], if starting with 0,0, the result will be [1].
        int[] curr = new int[]{0, -1};
        int direction = RIGHT;

        while(validForward(matrix, curr, direction, limits)) {
            curr = walkToEnd(matrix, curr, direction, limits, result);
            direction = turn(direction);
        }

        return result;
    }

    private boolean validForward(int[][] matrix, int[] curr, int direction, int[] limits) {
        int i = curr[0];
        int j = curr[1];
        if (direction == RIGHT) {
            if (j + 1 < limits[RIGHT]) return true;
            return false;
        } else if (direction == DOWN) {
            if (i + 1 < limits[DOWN]) return true;
            return false;
        } else if (direction == LEFT) {
            if (j - 1 > limits[LEFT]) return true;
            return false;
        } else {
            if (i - 1 > limits[UP]) return true;
            return false;
        }
    }

    private int[] walkToEnd(int[][] matrix, int[] curr, int direction, int[] limits, List<Integer> result) {
        int i = curr[0];
        int j = curr[1];
        if (direction == RIGHT) {
            while(j + 1 < limits[RIGHT]) result.add(matrix[i][++j]);
            limits[RIGHT] --;
        } else if (direction == DOWN) {
            while(i + 1 < limits[DOWN]) result.add(matrix[++i][j]);
            limits[DOWN] --;
        } else if (direction == LEFT) {
            while(j - 1 > limits[LEFT]) result.add(matrix[i][--j]);
            limits[LEFT] ++;
        } else { //  if (direction == UP)
            while(i - 1 > limits[UP]) result.add(matrix[--i][j]);
            limits[UP] ++;
        }
        return new int[]{i, j};
    }

    private int turn(int from) {
        if (from == RIGHT) return DOWN;
        else if (from == DOWN) return LEFT;
        else if (from == LEFT) return UP;
        else return RIGHT;
    }

}
