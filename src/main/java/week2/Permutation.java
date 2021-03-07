import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int amount = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        String str;
        do {
            str = StdIn.readString();
            queue.enqueue(str);
        } while (!StdIn.isEmpty());

        for (int i = 0; i < amount; i++) {
            Iterator<String> iterator = queue.iterator();
            StdOut.println(iterator.next());
        }
    }
}
