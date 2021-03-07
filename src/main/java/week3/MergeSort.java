package week3;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.PriorityQueue;

// MergeSort: Arrays.sort()
public class MergeSort {

    // This class should not be instantiated.
    private MergeSort() { }

    // stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

        int i = lo; int j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if(i > mid)                     a[k] = aux[j++];
            else if(j > hi)                 a[k] = aux[i++];
            else if(less(aux[i], aux[j]))   a[k] = aux[i++];
            else                            a[k] = aux[j++];
        }
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {

        if(hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(aux, a, lo, mid); // keep reverse aux and a
        sort(aux, a, mid+1, hi);
        merge(a, aux, lo, mid, hi);
        assert isSorted(a, lo, hi);
    }

    public static void sort(Comparable[] a) {
        Comparable[] aux = a.clone(); // duplicate a to aux
        sort(a, aux, 0, a.length-1);
        assert isSorted(a);
    }

    private static void bottomUpMerge(Comparable[] a, Comparable[] b, int low, int mid, int high) {

        int highIndex = mid;
        int lowIndex = low;
        int k = low;
        while (k <= high) {
            if (highIndex > high) b[k] = a[lowIndex++];
            else if (lowIndex >= mid) b[k] = a[highIndex++];
            else {
                int comp = a[lowIndex].compareTo(a[highIndex]);
                if (comp < 0) {
                    b[k] = a[lowIndex++];
                } else if (comp >= 0) {
                    b[k] = a[highIndex++];
                }
            }
            k ++;
            new PriorityQueue();
        }

        for(int j = low; j <= high; j++) {
            a[j] = b[j];
        }
    }

    private static void bottomUpMergeSortSub(Comparable[] a, Comparable[] b) {
        if (a.length <= 1) return;
        int size = 1;
        int index;
        while (size < a.length) {
            size *= 2;
            index = 0;
            while (index < a.length) {
                int low = index;
                int high = index + size > a.length ? a.length - 1 : index + size - 1;
                int mid = low + Math.round((high - low + 1)/2);
                bottomUpMerge(a, b, low, mid, high);
                index += size;
            }
        }
    }

    public static void bottomUpMergeSort(Comparable[] a) {
        Comparable[] b = a.clone();
        bottomUpMergeSortSub(a, b);
    }

    /***************************************************************************
     *  Helper sorting function.
     ***************************************************************************/

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
    }

    public Iterable<String> keys() {
        Queue<String> q = new Queue<>();
        inorder(q);
        return q;
    }

    public void inorder(Queue<String> q) {
        q.enqueue("test1");
        q.enqueue("test2");
        q.enqueue("test3");
        q.enqueue("test4");
    }

    private class MyComp<Integer> implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            return 0;
        }
    }
    /**
     * Reads in a sequence of strings from standard input; mergesorts them;
     * and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {


        MergeSort m = new MergeSort();
        System.out.println(m.keys());
        String[] a = {"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        MergeSort.bottomUpMergeSort(a);
        show(a);
        String[] c = {"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        MergeSort.sort(c);
        System.out.println();
        show(c);
    }
}

