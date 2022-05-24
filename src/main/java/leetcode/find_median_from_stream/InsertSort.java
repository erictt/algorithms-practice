package leetcode.find_median_from_stream;

import java.util.ArrayList;
import java.util.List;

// won't pass all tests
// https://leetcode.com/submissions/detail/699019987/
public class InsertSort {

    private List<Integer> numbers = new ArrayList<>();

    public void addNum(int num) {
        numbers.add(num);
        int i = numbers.size() - 1;
        while (i > 0 && num < numbers.get(i-1)) {
            numbers.set(i, numbers.get(i-1));
            numbers.set(i-1, num);
            i--;
        }
    }

    public double findMedian() {
        if (numbers.size() % 2 == 0) {
            return (numbers.get(numbers.size() / 2) + numbers.get(numbers.size() / 2 - 1)) * 0.5;
        } else {
            return numbers.get(numbers.size() / 2) * 1.0;
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */