package leetcode.distinct_subsequences;

public class DP {

    public int numDistinct(String s, String t) {

        int[][] memo = new int[s.length()+1][t.length()+1];
        for (int i = 0; i <= s.length(); i++) {
            memo[i][t.length()] = 1;
        }

        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = t.length() - 1; j >= 0; j--) {
                memo[i][j] = memo[i+1][j];
                if (s.charAt(i) == t.charAt(j)) memo[i][j] += memo[i+1][j+1];
            }
        }

        return memo[0][0];
    }

}
