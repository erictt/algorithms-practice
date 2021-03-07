package week3;

import edu.princeton.cs.algs4.StdOut;

public class QuickSort {

    private static void sort(Comparable[] a, int low, int high) {
        if (low >= high) return;

        int left = low + 1, right = high;

        // this way, we mark the anchor index as fixed.
        // another way is we only mark it's value, and start to compare with it.
        int anchor = low;
        int index = left;

        while (index <= right) {
            int comp = a[index].compareTo(a[anchor]);
            if (comp > 0) {
                swap(a, index, right);
                right --;
            } else if (comp < 0) {
                swap(a, left, index);
                index ++;
                left ++;
            } else {
                index ++;
            }
        }

        // if there is no one == anchor, then left = index and left end up = right+1
            // swap the anchor with the left-1 because
        // if there is one == anchor, then left < index and (left is possible = anchor or equal values been swapped to right.)
            // either way, we can confirm that left-1 is less than or is anchor.
            // swap the anchor with the left-1 because
        // left is the one that able to swap to the right
        swap(a, anchor, left - 1);

        sort(a, low, left - 1);
        sort(a, left + 1, high);
    }

    private static void sort2(Comparable[] a, int low, int high) {
        int left = low, right = high;

        Comparable anchor = a[low];
        int index = left + 1;
        while (index <= right) {
            int comp = a[index].compareTo(anchor);
            if (comp > 0) {
                swap(a, index, right);
                right --;
            } else if (comp < 0) {
                swap(a, index, left);
                left ++;
                index ++;
            } else {
                index ++;  // we keep the possible equal value to the right
            }
        }

        // to break the while, index has to +1, that means the current index-1 is less than anchor
        // and index is >= anchor

        sort(a, low, index - 1);
        sort(a, low, index);
    }

    private static void swap(Comparable[] a, int x, int y) {
        Comparable temp = a[y];
        a[y] = a[x];
        a[x] = temp;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
    }

    public static void main(String[] args) {
        String[] a = {"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        QuickSort.sort2(a, 0, a.length - 1);
        show(a);
    }
}
