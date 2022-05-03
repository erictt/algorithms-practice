package leetcode.three_sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://leetcode.com/problems/3sum/
/*
 * There are several places that i missed:
 * 1. always put range condition in front of others. e.g. i < nums.length - 2 should be in front of nums[i] == nums[i-1]
 * 2. in case: [-1, -1, -1, 0, 1, 2]. it's easy to miss [-1, 0, 1]
 *      because the while (nums[i] == nums[i-1]) skipped all -1 after matching [-1, -1, 2]
 *      which is why we should pass res to the twoSum method and let it continue to search in case there are more than 1 answers when i = -1.
 * 3.  when res.add(Arrays.asList(nums[i], nums[j++], nums[k--]));, it's important to move both pointers
 *      because the following while might change nothing which will cause infinite loop and add the same result over and over again.
 */
public class TwoPointers {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);

        List<List<Integer>> res = new ArrayList<>();

        int i = 0;
        while (i < nums.length - 2 && nums[i] <= 0) {
            twoSum(nums, i, res);
            i++;
            while (i < nums.length - 2 && nums[i] == nums[i-1]) i++;
        }
        return res;
    }

    private void twoSum(int[] nums, int i, List<List<Integer>> res) {
        int target = - nums[i];
        int j = i + 1, k = nums.length - 1;

        while (j < k) {
            if (nums[j] + nums[k] < target) j++;
            else if (nums[j] + nums[k] > target) k--;
            else {
                res.add(Arrays.asList(nums[i], nums[j++], nums[k--]));
                while (j < k && nums[j] == nums[j-1]) j++;
                while (j < k && k < nums.length - 2 && nums[k] == nums[k+1]) k--;
            }
        }
    }
}
