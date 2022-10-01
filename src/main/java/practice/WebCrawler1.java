package practice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

interface HtmlParser {
    List<String> htmlParser(String url);
}

public class WebCrawler1 {

    private HtmlParser htmlParser;
    private static final int THREAD_COUNT = 5;
    private ExecutorService executor;

    private Queue<Future<List<String>>> futures;

    public WebCrawler1() {
        ReentrantLock lock = new ReentrantLock();
        Condition c1 = lock.newCondition();
        this.executor = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    public List<String> crawl(String startUrl, HtmlParser htmlParser) {

        this.htmlParser = htmlParser;

        Set<String> visited = new HashSet<>();
        visited.add(startUrl);

        Future<List<String>> future = executor.submit(new MyCrawler(startUrl));
        futures.offer(future);

        String hostname = getHostname(startUrl);

        while (! futures.isEmpty()) {
            Future<List<String>> nextFuture = futures.peek();
            if (nextFuture.isDone()) {
                try {
                    List<String> urls = nextFuture.get();
                    for (String url: urls) {
                        if (! visited.contains(url) && validUrl(url, hostname)) {
                            futures.offer(executor.submit(new MyCrawler(url)));
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        executor.shutdown();

        return new ArrayList<>(visited);
    }

    private boolean validUrl(String url, String hostname) {
        return url.startsWith(hostname);
    }

    private String getHostname(String url) {
        // https://test.com
        // http://test.com
        int index = url.indexOf('/', 8);
        if (index == -1) return url;
        return url.substring(0, index);
    }

    private class MyCrawler implements Callable<List<String>> {

        String url;

        public MyCrawler(String url) {
            this.url = url;
        }

        @Override
        public List<String> call() {
            return htmlParser.htmlParser(url);
        }
    }

    public static void main(String[] args) {
        byte a = 127;
        System.out.println(a);
    }
}
