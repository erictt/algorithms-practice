package leetcode.k_closest_points;

import java.util.PriorityQueue;

// https://leetcode.com/problems/k-closest-points-to-origin/
public class PQ {
    private class Point {

        int i;
        int j;
        int value;

        Point(int i, int j) {
            this.i = i;
            this.j = j;
            this.value = i*i + j*j;
        }
    }
    public int[][] kClosest(int[][] points, int k) {

         PriorityQueue<Point> queue = new PriorityQueue<>((a, b) -> b.value - a.value); // max heap queue

         // we only need k points
         for (int i = 0; i < k; i++) { // O(k)
             queue.add(new Point(points[i][0], points[i][1])); // O(log(k))
         }

         // check all of the rest points, and compare it with the max one in queue
         // if it's less that the max, poll max, and add the new one in queue.
         for (int i = k; i < points.length; i++) { // O(n-k)
             Point max = queue.peek();
             Point newPoint = new Point(points[i][0], points[i][1]);
             if (max.value > newPoint.value) {
                 queue.poll(); // O(1)
                 queue.add(newPoint); // O(log(k))
             }
         }

         // n log(k)

         // poll all points from the queue and put it in the result array
         int index = 0;
         int[][] res = new int[queue.size()][2];
         while (! queue.isEmpty()) {
             Point p = queue.poll();
             res[index++] = new int[]{p.i, p.j};
         }

         return res; // O(k + n log(k)) ~= nlog(k)
     }

}
