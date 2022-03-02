package part2.week5;

import edu.princeton.cs.algs4.In;

public class CircularSuffixArray {

    private static final int R = 256;
    private final int length;
    private final int[] mapping;
    private final char[][] suffixes;

    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();

        length = s.length();
        mapping = new int[length];
        int[] mappingAux = new int[length]; // used for storing the assigned value temporarily

        suffixes = new char[length][length]; // will be sorted in-place
        char[][] aux = new char[length][length]; // used for sorting

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                suffixes[i][j] = s.charAt((i+j) % length);
            }
        }

        for (int i = 0; i < length; i++) {
            mapping[i] = i;
        }

        // key-indexed counting
        // https://cs.ericyy.me/algorithms-2/week-3/index.html#key-indexed-counting
        // the tricky part is the `+1` part: count[original[i][x]+1]
        // it's setting where the next char should start with
        sort(aux, mappingAux, 0, length-1, 0);
    }

    private void sort(char[][] aux, int[] mappingAux, int start, int end, int column) {

        int[] count = new int[R+1];
        for (int i = start; i <= end; i++) {
            count[suffixes[i][column]+1]++;
        }

        for (int r = 0; r < R; r++) {
            count[r+1] += count[r];
        }

        for (int i = start; i <= end; i++) {
            // since we're counting the next char,
            // in here, if count[a]=2, then aux[0], aux[1] will both be a (assume a = 0)
            aux[start+count[suffixes[i][column]]] = suffixes[i];
            mappingAux[start+count[suffixes[i][column]]++] = mapping[i];
        }

        for (int i = start; i <= end; i++) {
            suffixes[i] = aux[i];
            mapping[i] = mappingAux[i];
        }

        for (int r = 0; r < R; r++) {
            if (count[r+1] - count[r] > 1 && column < suffixes.length - 1) {
                sort(aux, mappingAux, start + count[r], start + count[r+1]-1, column+1);
            }
        }
    }

    // circular suffix array of s
//    public CircularSuffixArray(String s) {
//        if (s == null) throw new IllegalArgumentException();
//
//        length = s.length();
//        mapping = new int[length];
//        mappingAux = new int[length];
//
//        char[] charArr = s.toCharArray();
//        original = new char[length][length];
//        sorted = new char[length][length];
//        char[][] aux = new char[length][length];
//
//        for (int i = 0; i < length; i++) {
//            for (int j = 0; j < length; j++) {
//                original[i][j] = charArr[(i+j)%length];
//                sorted[i][j] = charArr[(i+j)%length];
//            }
//        }
//
//        for (int i = 0; i < length; i++) {
//            mapping[i] = i;
//        }
//
//        // need to loop log_2^N times
//        int loopTimes = (int)Math.ceil((Math.log(length)/Math.log(2)));
//        int size = 2;
//        for (int i = 1; i <= loopTimes; i++) {
//            int startIndex = 0;
//            while (startIndex < length) {
//                int middle = startIndex+size/2-1;
//                sort(sorted, aux, startIndex, middle, Math.min(middle+size/2, length-1));
//                startIndex += size;
//            }
//
//            // swap
//            char[][] temp = sorted;
//            sorted = aux;
//            aux = temp;
//
//            // swap
//            int[] tempM = mapping;
//            mapping = mappingAux;
//            mappingAux = tempM;
//
//            // increase size
//            size *= 2;
//        }
//    }

//    // bottom-up merge sort
//    private void sort(char[][] suffixes, char[][] aux, int start, int middle, int end) {
//
//        if (start == end) return;
//
//        int fillIndex = start;
//        int lIndex = start;
//        int rIndex = middle + 1;
//        while (lIndex <= middle && rIndex <= end) {
//            if (bigger(suffixes[lIndex], suffixes[rIndex])) {
//                mappingAux[fillIndex] = mapping[rIndex];
//                aux[fillIndex++] = suffixes[rIndex++];
//            } else {
//                mappingAux[fillIndex] = mapping[lIndex];
//                aux[fillIndex++] = suffixes[lIndex++];
//            }
//        }
//
//        while (lIndex <= middle) {
//            mappingAux[fillIndex] = mapping[lIndex];
//            aux[fillIndex++] = suffixes[lIndex++];
//        }
//        while (rIndex <= end) {
//            mappingAux[fillIndex] = mapping[rIndex];
//            aux[fillIndex++] = suffixes[rIndex++];
//        }
//    }

//    private boolean bigger(char[] a, char[] b) {
//        int i = 0;
//        while (a[i] == b[i]) {
//            i++;
//        }
//        return a[i] > b[i];
//    }

    // length of s
    public int length() {
        return length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        return mapping[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);

        StringBuilder sb = new StringBuilder();
        while (in.hasNextLine()) {
            sb.append(in.readLine());
        }
        String s = "ABAAABAABABAAABABABBABBAABBABAABABAAABABBBAAAAAABB";
        CircularSuffixArray csa = new CircularSuffixArray(s);
        System.out.println(csa.length());
        for (int i = 0; i < s.length(); i++) {
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