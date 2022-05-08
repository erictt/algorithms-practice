package leetcode.search_in_rotated_array;

// https://leetcode.com/problems/search-in-rotated-sorted-array/
public class BinarySearch {
    public int search(int[] nums, int target) {
        if (nums.length == 1) return target == nums[0] ? 0 : -1;
        return search(nums, target, 0, nums.length-1);
    }

    // key: if the entire left part is monotonically increasing, which means the pivot point is on the right part
    private int search(int[] nums, int target, int low, int high) {

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) return mid;
            if (nums[mid] >= nums[low]) { // don't forget the = condition!
                if (target >= nums[low] && target < nums[mid]) // only this situation, we can do binary search
                    return binarySearch(nums, target, low, mid - 1);
                else low = mid + 1;
            } else {
                if (target > nums[mid] && target <= nums[high])
                    return binarySearch(nums, target, mid + 1, high);
                else high = mid - 1;
            }
        }

        return -1;
    }

    private int binarySearch(int[] nums, int target, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }
}
