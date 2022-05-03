package leetcode.search_unkown_size_array;

/**
 * // This is ArrayReader's API interface.
 * // You should not implement it, or speculate about its implementation
 */
 interface ArrayReader {
     public int get(int index);
 }

 // https://leetcode.com/problems/search-in-a-sorted-array-of-unknown-size/
public class BinarySearch {
    public int search(ArrayReader reader, int target) {

        if (reader.get(0) == target) return 0;

        int start = 0;
        int end = 1;
        while (reader.get(end) < target) {
            start = end;
            end *= 2;
        }

        return search(reader, target, start, end);

//         int mid = 1;

//         while (reader.get(mid) != Integer.MAX_VALUE) { // in another word, this means reader.get(mid) > target
//             int val = reader.get(mid);
//             if (val == target) return mid;
//             else if (val > target) {
//                 return search(reader, target, mid / 2, mid);
//             } else { // val < target means target is beyond mid
//                 mid *= 2;
//             }
//         }

        // return search(reader, target, mid / 2, mid);
    }

    private int search(ArrayReader reader, int target, int start, int end) {
        while (start < end) {
            int mid = (start + end + 1) / 2;
            if (reader.get(mid) > target) end = mid - 1;
            else if (reader.get(mid) < target) start = mid;
            else return mid;
        }
        return -1;
    }
}
