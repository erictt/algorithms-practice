package leetcode.distinct_subsequences;

public class DFS {

    public int numDistinct(String s, String t) {

        Integer[][] memo = new Integer[s.length()][t.length()];
        return dfs(memo, s, t, 0, 0);
    }

    private int dfs(Integer[][] memo, String s, String t, int i, int j) {
        if (j == t.length()) return 1;
        if (i == s.length()) return 0;

        if (memo[i][j] != null) return memo[i][j];

        int sum = 0;
        if (s.charAt(i) == t.charAt(j)) sum += dfs(memo, s, t, i+1, j+1);
        sum += dfs(memo, s, t, i+1, j);
        memo[i][j] = sum;

        return sum;
    }
}
