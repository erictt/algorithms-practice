import java.util.HashSet;
import java.util.Set;

class Solution {

    public int search(int[] nums, int target) {

        int i = 0, j = nums.length - 1;
        while (i < j) {
            int mid = (i + j + 1) / 2;
            if (nums[mid] >= target) j = mid - 1;
            else i = mid;
        }
        return i+1 < nums.length && nums[i+1] == target? i+1 : -1;
    }
    public static void main(String[] main) {
        Solution s = new Solution();
        int index = s.search(new int[]{1,2,4,4,5,7,9}, 3);
        System.out.println(index);
    }
}