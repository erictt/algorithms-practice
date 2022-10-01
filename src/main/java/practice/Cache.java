//import java.io.*;
//import java.net.*;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.PriorityQueue;
//import java.util.TreeMap;
//import java.util.concurrent.TimeUnit;
//
//public class Cache {
//
//    private DateTimeFormatter dateFormat = DateTimeFormatter.BASIC_ISO_DATE;
//    private class Page {
//        String url;
//        String data;
//        LocalDateTime expire;
//
//        Page(String url,String data, LocalDateTime expire) {
//            this.data = data;
//            this.url = url;
//            this.expire = expire;
//        }
//    }
//    private Map<String, Page> map;
//    private TreeMap<String, Page> ttl;
//
//    private Thread cleanThread;
//
//    private class CacheCleaner implements Runnable {
//
//        @Override
//        public void run() {
//            while (true) {
//                System.out.println("run thread ");
//                if (! ttl.isEmpty()) {
//                    LocalDateTime first = ttl.firstKey();
//                    if (first.compareTo(LocalDateTime.now()) <= 0) {
//                        ttl.remove(first);
//                    }
//                }
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }
//
//    public Cache() {
//        map = new HashMap<>();
//        ttl = new TreeMap<>((a, b) -> map.get(a).compareTo(map.get(b)));
//        cleanThread = new Thread(new CacheCleaner());
//        cleanThread.setDaemon(true);
//    }
//
//    public void startCleanThread() {
//        cleanThread.start();
//    }
//
//    public void cachePage(String pageUrl, String pageData, long ttlSeconds) {
//        ttl.remove()
//        map.put(pageUrl, pageData);
//        ttl.put(LocalDateTime.now().plusSeconds(ttlSeconds), pageUrl);
//    }
//
//    public String getPage(String pageUrl) {
//        return map.get(pageUrl);
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//
//        Cache cache = new Cache();
//        cache.startCleanThread();
////        cache.cachePage("page1", "page1 content", 100);
////        cache.cachePage("page2", "page2 content", 5);
////        cache.cachePage("page3", "page3 content", 7);
////        cache.cachePage("page4", "page4 content", 50);
////
////        for (int i = 0; i < 10; i++) {
////            System.out.println(cache.getPage("page1"));
////            System.out.println(cache.getPage("page2"));
////            System.out.println(cache.getPage("page3"));
////            System.out.println(cache.getPage("page4"));
////            System.out.println(i + "======================");
////            Thread.sleep(1000);
////        }
//        if (args.length < 1) return;
//
////        int port = Integer.parseInt(args[0]);
//        int port = 8080;
//
//        try (ServerSocket serverSocket = new ServerSocket(port)) {
//
//            System.out.println("Server is listening on port " + port);
//
//            while (true) {
//                Socket socket = serverSocket.accept();
//
//                System.out.println("New client connected");
//
//                InputStream input = socket.getInputStream();
//                OutputStream output = socket.getOutputStream();
//                PrintWriter writer = new PrintWriter(output, true);
//                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//                String pageUrl = reader.readLine();
//                if (pageUrl.isEmpty()) {
//
//                }
//                StringBuilder data = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    data.append(line).append("\n");
//                }
//                cache.cachePage(pageUrl, data.toString(), 10);
//
////                writer.println(new Date().toString());
//                writer.println("Done");
//            }
//
//        } catch (IOException ex) {
//            System.out.println("Server exception: " + ex.getMessage());
//            ex.printStackTrace();
//        }
//    }
//}