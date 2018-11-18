package week3;

import edu.princeton.cs.algs4.StdOut;

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
            StdOut.println(a[i]);
        }
    }

    /**
     * Reads in a sequence of strings from standard input; mergesorts them;
     * and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String[] a = {"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        MergeSort.sort(a);
        show(a);
    }
}

