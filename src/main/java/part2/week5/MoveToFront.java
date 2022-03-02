package part2.week5;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/*
java-algs4 -classpath ./target/classes part2.week5.MoveToFront - < ./algs4-data/burrows/abra.txt | java-algs4 edu.princeton.cs.algs4.HexDump 16
 */
public class MoveToFront {

    private static final int R = 256;
    private static final Node[] NODES = new Node[R];

    private static class Node {
        char c;

        Node previous;
        Node next;

        Node(char c) {
            this.c = c;
        }
    }

    private static void init() {
        for (int i = 0; i < R; i++) {
            NODES[i] = new Node((char) i);
        }
        for (int i = 0; i < R; i++) {
            if (i != R-1) NODES[i].next = NODES[i+1];
            if (i != 0) NODES[i].previous = NODES[i-1];
        }
    }

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        init();
        Node firstNode = NODES[0];
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            BinaryStdOut.write(findIndex(firstNode, NODES[c]), 8);
            moveNodeToFirst(firstNode, NODES[c]);
            firstNode = NODES[c];
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        init();
        Node firstNode = NODES[0];
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            Node targetNode = findTargetNode(firstNode, c);
            BinaryStdOut.write(targetNode.c);
            moveNodeToFirst(firstNode, targetNode);
            firstNode = targetNode;
        }
        BinaryStdOut.close();
    }

    // a is the first node in the linked list
    private static void moveNodeToFirst(Node firstNode, Node current) {
        if (firstNode.c == current.c) return;
        current.next.previous = current.previous;
        current.previous.next = current.next;
        current.next = firstNode;
        current.previous = null;
        firstNode.previous = current;
    }

    private static int findIndex(Node firstNode, Node target) {
        Node current = firstNode;
        int i = 0;
        while (current != null && current.c != target.c) {
            current = current.next;
            i++;
        }
        return i;
    }

    private static Node findTargetNode(Node firstNode, int index) {
        Node current = firstNode;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}