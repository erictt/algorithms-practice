package part2.week5;

import edu.princeton.cs.algs4.In;

public class CircularSuffixArray {

    private static final int R = 256;
    private final int length;
    private final int[] mapping;

    private final String str;

    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();

        length = s.length();
        str = s;

        // instead of constructing a matrix, I used an indexed CircularSuffix array to represent the matrix
        CircularSuffix[] suffixes = new CircularSuffix[length]; // will be sorted in-place
        for (int i = 0; i < length; i++) {
            suffixes[i] = new CircularSuffix(i);
        }

        mapping = new int[length];

        for (int i = 0; i < length; i++) {
            mapping[i] = i;
        }

        // key-indexed counting
        // https://cs.ericyy.me/algorithms-2/week-3/index.html#key-indexed-counting
        // the tricky part is the `+1` part: count[original[i][x]+1]
        // it's setting where the next char should start with
        sort(suffixes, 0, length-1, 0);
    }

    // simulate different suffix by constructing it with a start index.
    private class CircularSuffix {
        int start;

        CircularSuffix(int start) {
            this.start = start;
        }

        int charAt(int i) {
            return i >= str.length() ? -1 : str.charAt((start+i) % str.length());
        }
    }

    private void sort(CircularSuffix[] suffixes, int start, int end, int column) {

        if (end <= start) return;

        int lt = start, gt = end;
        int v = suffixes[start].charAt(column);
        int i = start + 1;

        while (i <= gt) {
            int t = suffixes[i].charAt(column);
            if (t < v) {
                CircularSuffix temp = suffixes[lt];
                suffixes[lt] = suffixes[i];
                suffixes[i] = temp;
                int x = mapping[i];
                mapping[i] = mapping[lt];
                mapping[lt] = x;
                i++;
                lt++;
            } else if (t > v) {
                CircularSuffix temp = suffixes[gt];
                suffixes[gt] = suffixes[i];
                suffixes[i] = temp;
                int x = mapping[i];
                mapping[i] = mapping[gt];
                mapping[gt] = x;
                gt--;
            } else i++;
        }

        sort(suffixes, start, lt-1, column);
        if (v >= 0) sort(suffixes, lt, gt, column+1);
        sort(suffixes, gt+1, end, column);
    }

    // length of s
    public int length() {
        return length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length) throw new IllegalArgumentException();
        return mapping[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);

        StringBuilder sb = new StringBuilder();
        while (in.hasNextLine()) {
            sb.append(in.readLine());
        }
//        sb = new StringBuilder("BAB");
        CircularSuffixArray csa = new CircularSuffixArray(sb.toString());
        System.out.println(csa.length());
        for (int i = 0; i < sb.length(); i++) {
            System.out.print(csa.index(i));
            System.out.print(" ");
        }
        System.out.println();

//        while (in.hasNextLine()) {
//            CircularSuffixArray csa = new CircularSuffixArray(in.readLine());
//            System.out.println("  i     Original Suffixes           Sorted Suffixes         index[i]");
//            System.out.println(" --   -----------------------    -----------------------    ------ ");
//            for (int i = 0; i < csa.length(); i++) {
//                if (i < 10) System.out.print(" ");
//                System.out.print(" " + i + "   ");
//                for (int j = 0; j < csa.length(); j++) {
//                    System.out.print(csa.original[i][j] + " ");
//                }
//                System.out.print("   ");
//                for (int j = 0; j < csa.length(); j++) {
//                    System.out.print(csa.sorted[i][j] + " ");
//                }
//                System.out.print("   ");
//                System.out.println(csa.mapping[i]);
//            }
//        }
    }
}