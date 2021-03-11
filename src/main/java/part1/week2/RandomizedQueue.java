package part1.week2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        items = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == items.length) resize(items.length * 2);
        items[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size < 1) throw new NoSuchElementException();
        if (size < items.length / 2) resize(items.length);
        int i = StdRandom.uniform(size);
        swap(i, size-1);
        Item item = items[size-1];
        items[size-1] = null;
        size--;
        return item;
    }

    private void resize(int len) {
        Item[] newItems = (Item[]) new Object[len];
        for (int i = 0; i < items.length; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

    private void swap(int i, int j) {
        Item temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size < 1) throw new NoSuchElementException();
        return items[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<Item> {

        int index;
        int end;

        public Iter() {
            int start;
            if (size == 0)  start = 0;
            else start = StdRandom.uniform(size);
            index = start;
            end = size + start;
        }

        @Override
        public boolean hasNext() {
            if (index < end) return true;
            return false;
        }

        @Override
        public Item next() {
            if (index >= end) throw new NoSuchElementException();
            Item item = items[index % size];
            index++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 10;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
//        for (int i = 0; i < 5; i++)
//            queue.dequeue();
        for (int a : queue) {
            StdOut.print(a + " ");
        }
        StdOut.println();
        for (int b : queue) {
            StdOut.print(b + " ");
        }
    }
}
