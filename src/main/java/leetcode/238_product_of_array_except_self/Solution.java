class Solution {
    public int[] productExceptSelf(int[] nums) {

        int[] productLeft = new int[nums.length];
        productLeft[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            productLeft[i] = productLeft[i-1] * nums[i];
        }
        int[] productRight = new int[nums.length];
        productRight[nums.length-1] = nums[nums.length-1];
        for (int i = nums.length - 2; i >= 0; i--) {
            productRight[i] = productRight[i+1] * nums[i];
        }

        int[] results = new int[nums.length];
        results[0] = productRight[1];
        results[nums.length-1] = productLeft[nums.length-2];
        for (int i = 1; i < nums.length - 1; i++) {
            results[i] = productLeft[i-1] * productRight[i+1];
        }

        return results;
    }
}
