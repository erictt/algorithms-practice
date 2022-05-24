package leetcode.find_median_from_stream;

import java.util.PriorityQueue;

public class TwoHeap {

    private PriorityQueue<Integer> left;
    private PriorityQueue<Integer> right;

    public TwoHeap() {
        left = new PriorityQueue<>((a,b) -> b - a); // max heap
        right = new PriorityQueue<>((a,b) -> a - b); // min heap
    }

    public void addNum(int num) {
        right.add(num);
        left.add(right.poll());
        if (right.size() < left.size()) {
            right.add(left.poll());
        }
    }

    public double findMedian() {
        if (right.size() > left.size()) return right.peek() * 1.0;
        else return (left.peek() + right.peek()) / 2.0;
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
