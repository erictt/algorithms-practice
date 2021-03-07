import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private static class Node<Item> {
        Node<Item> from = null;
        Node<Item> to = null;
        Item item;

        Node(Item item) {
           this.item = item;
        }
    }

    private Node<Item> head = null;
    private Node<Item> tail = null;
    private int size = 0;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        if (head == tail && head == null) return true;
        return false;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (head == null) {
            head = new Node<>(item);
            tail = head;
        } else {
            Node<Item> temp = head;
            head = new Node<>(item);
            head.to = temp;
            temp.from = head;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (tail == null) {
            tail = new Node<>(item);
            head = tail;
        } else {
            Node<Item> temp = tail;
            tail = new Node<>(item);
            tail.from = temp;
            temp.to = tail;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (head == null) throw new NoSuchElementException();
        else {
            Item item = head.item;
            head = head.to;
            if (head == null) tail = null;
            else head.from = null;
            size--;
            return item;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (head == null) throw new NoSuchElementException();
        else {
            Item item = tail.item;
            tail = tail.from;
            if (tail == null) head = null;
            else tail.to = null;
            size--;
            return item;
        }
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<Item> {
        Node<Item> cursor;

        Itr() {
            cursor = new Node<>(null);
            cursor.to = head;
        }

        @Override
        public boolean hasNext() {
            return cursor.to != null;
        }

        @Override
        public Item next() {
            if (cursor.to == null) throw new NoSuchElementException();
            cursor = cursor.to;
            return cursor.item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("1");
        deque.addFirst("2");
        deque.addFirst("3");
        StdOut.println(deque.size());
        deque.addFirst("4");
        deque.removeFirst();
        deque.addLast("5");
        deque.addLast("6");
        StdOut.println(deque.size());
        deque.removeLast();
        StdOut.println(deque.size());
        Iterator<String> ite = deque.iterator();
        while (ite.hasNext()) {
            StdOut.print(ite.next() + " ");
            StdOut.println();
        }
    }

}
