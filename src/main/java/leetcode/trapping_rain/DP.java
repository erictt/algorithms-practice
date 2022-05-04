package leetcode.trapping_rain;

// https://leetcode.com/problems/trapping-rain-water/
public class DP {
     public int trap(int[] height) {

         // the idea is to find the left max and right max for each bar by using two loops
         // the first loop from left to right, and track the max
         // the second loop from right to left, and track the max
         // the current bar's max is either itself or the left max or the right max
         // if the current bar is lower than both left and right, then we can trap some water.
         int[] leftMax = new int[height.length];
         int[] rightMax = new int[height.length];

         leftMax[0] = height[0];
         for (int i = 1; i < height.length; i++) {
             leftMax[i] = Math.max(leftMax[i-1], height[i]);
         }

         rightMax[height.length - 1] = height[height.length - 1];
         for (int i = height.length - 2; i >= 0; i--) {
             rightMax[i] = Math.max(rightMax[i+1], height[i]);
         }

         int sum = 0;
         for (int i = 1; i < height.length - 1; i++) {
             sum += Math.min(leftMax[i], rightMax[i]) - height[i];
         }
         return sum;
     }
}
