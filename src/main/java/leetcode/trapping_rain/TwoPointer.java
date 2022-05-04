package leetcode.trapping_rain;

// https://leetcode.com/problems/trapping-rain-water/
public class TwoPointer {
    public int trap(int[] height) {
        // the idea is only compare the current bar with left or right maximum
        // we use two pointers, one move from left to right and the other move from right to left.
        // when left max < right max, we compare the left pointer with left max, and move left pointer.
        // in other case we compare the right pointer with right max, and move right pointer.
        int leftMax = height[0];
        int rightMax = height[height.length - 1];

        int sum = 0;
        int i = 1, j = height.length - 2;
        while (i <= j) {
            // in this case, we compare the i with left max since we know leftmax is smaller than rightmax,
            // and current bar will be able to have leftMax - height[i] water.
            if (leftMax <= rightMax) {
                if (height[i] < leftMax) sum += leftMax - height[i];
                else leftMax = height[i];
                i++;
            } else {
                if (height[j] < rightMax) sum += rightMax - height[j];
                else rightMax = height[j];
                j--;
            }
        }

        return sum;
    }
}