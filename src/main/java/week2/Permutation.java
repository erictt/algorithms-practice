package week2;

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

//        Scanner sc;
//        try {
//          sc = new Scanner(new File(args[1]));
//          while (sc.hasNextLine()) {
//              String data = sc.nextLine();
//              String[] list = data.split(" ");
//              for (String s: list) {
//                  queue.enqueue(s);
//              }
//          }
//          sc.close();
//        } catch (FileNotFoundException e) {
//          e.printStackTrace();
//        }

        for (int i = 0; i < amount; i++) {
            Iterator<String> iterator = queue.iterator();
            StdOut.println(iterator.next());
        }
    }
}
