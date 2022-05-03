package leetcode.two_sum;

import java.util.HashMap;
import java.util.Map;

public class MapSolution {

    public int[] twoSum(int[] nums, int target) {

        // be aware that you can't use sort + two pointer, because this problem needs to return the indices
        // sort will disturb that
        Map<Integer, Integer> subs = new HashMap<>();
        subs.put(target - nums[0], 0);
        for (int i = 1; i < nums.length; i++) {
            if (subs.containsKey(nums[i]))
                return new int[]{subs.get(nums[i]), i};
            subs.put(target - nums[i], i);
        }
        return new int[]{-1, -1};
    }
}
