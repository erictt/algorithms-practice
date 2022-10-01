package practice;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class RateLimit {


    private static final int RATE = 300;

    private Deque<Bucket> buckets = new LinkedList<>();

    private int curIndex = 0;

    public void hit() {
        Bucket peek = buckets.peekFirst();
        int t = now();
        if (peek == null || peek.timestamp != t) {
            buckets.offerFirst(new Bucket(t));
        } else {
            peek.bumpHit();
        }

        if (buckets.size() > RATE) buckets.pollLast();
    }

    public int countLast5Min() {
        Iterator<Bucket> iterable = buckets.iterator();
        int count = 0;
        int t = now();
        while (iterable.hasNext()) {
            Bucket next = iterable.next();
            if (t - next.timestamp < 300) count += next.hit;
        }
        return count;
    }

    private int now() {
        return (int)(System.nanoTime() / 10000000); // seconds
    }

    private class Bucket {
        int timestamp;
        int hit;

        ReentrantLock lock = new ReentrantLock();

        Bucket(int timestamp) {
            this.timestamp = timestamp;
            hit = 1;
        }

        void bumpHit() {
            lock.lock();
            try {
                hit += 1;
                System.out.println("try");
            } finally {
                System.out.println("final");
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RateLimit rl = new RateLimit();
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(10);
            rl.hit();
        }
        System.out.println(rl.countLast5Min());

    }
}
