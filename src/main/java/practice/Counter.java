package practice;

public class Counter {

    private int count = 0;

    public void increase() {
        synchronized (this) {
            count += 1;
        }
    }
}
