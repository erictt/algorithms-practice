package leetcode.k_closest_points;

import java.util.Arrays;

// https://leetcode.com/problems/k-closest-points-to-origin/
public class QuickSelect {
    public int[][] kClosest(int[][] points, int k) {

        if (points.length == k) return points;

        int left = 0, right = points.length - 1;

        int mid = right + 1;

        while (left < right) {
            mid = partition(points, left ,right);
            if (mid == k) break;
            else if (mid > k) right = mid - 1;
            else left = mid;
        }

        return Arrays.copyOf(points, k);
    }

    private int partition(int[][] points, int left, int right) {

        int mid = (left + right) / 2;
        int midDis = distance(points[mid]);
        while (left < right) {
            while (left < right && distance(points[left]) < midDis) left++;
            // while (left < right && distance(points[right]) > midDis) right--;
            // [[1,3],[-2,2], [2,-2]] 2 is why can't while right--;
            if (left >= right) break;

            int[] temp = points[left];
            points[left] = points[right];
            points[right] = temp;
            right--;
            if (distance(points[left]) < midDis) left++;
        }

        // it's possible that left have crossed right, and still less than mid.
        if (distance(points[left]) < midDis) left++;

        // case: [[1,3],[-2,2], [2,-2]] 2
        // left didn't move in the first two loop, but the second loop move 1,3 to the middle
        // then the third loop will move -2,2 and 2,-2 the the front.

        return left;
    }


    private int distance(int[] p) {
        return p[0] * p[0] + p[1] * p[1];
    }

    public static void main(String[] main) {
        String str = "*";
        System.out.println(str == "*");
    }
}
