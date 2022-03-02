import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    private static final int R = 256;

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        StringBuilder sb = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) {
            sb.append(BinaryStdIn.readChar());
        }
        CircularSuffixArray csa = new CircularSuffixArray(sb.toString());
        char[] lastSuffixes = new char[sb.length()];
        int start = 0;
        for (int i = 0; i < sb.length(); i++) {
            int index = csa.index(i)-1;
            if (index < 0) index += csa.length();
            lastSuffixes[i] = sb.charAt(index);
            if (csa.index(i) == 0)  start = i;
        }

        BinaryStdOut.write(start);
        for (int i = 0; i < sb.length(); i++) {
            BinaryStdOut.write(lastSuffixes[i], 8);
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output

    /* An example for ABRACADABRA!
     *   i      Sorted Suffixes     t      next[i]
     *  --    -----------------------      -------
     *   0    ! ? ? ? ? ? ? ? ? ? ? A        3
     *   1    A ? ? ? ? ? ? ? ? ? ? R        0
     *   2    A ? ? ? ? ? ? ? ? ? ? D        6
     *  *3    A ? ? ? ? ? ? ? ? ? ? !        7
     *   4    A ? ? ? ? ? ? ? ? ? ? R        8
     *   5    A ? ? ? ? ? ? ? ? ? ? C        9
     *   6    B ? ? ? ? ? ? ? ? ? ? A       10
     *   7    B ? ? ? ? ? ? ? ? ? ? A       11
     *   8    C ? ? ? ? ? ? ? ? ? ? A        5
     *   9    D ? ? ? ? ? ? ? ? ? ? A        2
     *  10    R ? ? ? ? ? ? ? ? ? ? B        1
     *  11    R ? ? ? ? ? ? ? ? ? ? B        4
     */
    // the part that confused me is how can we get the first column with only the last column.
    // turns out it's just a sorted version of last column. I didn't connect the idea of circular suffix array here.
    public static void inverseTransform() {

        int[] count = new int[R+1];
        int index = BinaryStdIn.readInt();
        StringBuilder sb = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            count[c+1]++;
            sb.append(c);
        }

        for (int i = 0; i < R; i++) {
            count[i+1] += count[i];
        }

        int[] next = new int[sb.length()];

        for (int i = 0; i < sb.length(); i++) {
            next[count[sb.charAt(i)]++] = i;
        }

        int j = index;
        for (int i = 0; i < sb.length(); i++) {
            BinaryStdOut.write(sb.charAt(next[j]), 8);
            j = next[j];
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if      (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}