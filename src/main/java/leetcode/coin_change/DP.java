package leetcode.coin_change;

public class DP {
    public int coinChange(int[] coins, int amount) {
        if (amount == 0) return 0;
        return coinChange(coins, amount, new int[amount+1]);
    }

    private int coinChange(int[] coins, int amount, int[] calculated) {

        for (int i: coins) {
            if (amount == i) return 1;
            if (i > amount) continue; // don't forget to deal with the single coin that bigger than amount
            calculated[i] = 1;
        }
        for (int i = 0; i <= amount; i++) {
            if (calculated[i] == 0) continue;
            for (int j: coins) {
                if (i+j > amount || i+j < 0) continue; // i+j may exceed MAX_VALUE
                if (calculated[i+j] == 0)
                    calculated[i+j] = calculated[i] + 1;
                else // it's possible that later on, the coins that i+j needs are less than the first time we set up
                    calculated[i+j] = Math.min(calculated[i] + 1, calculated[i+j]);
            }
        }
        return calculated[amount] == 0 ? -1 : calculated[amount];
    }
}
