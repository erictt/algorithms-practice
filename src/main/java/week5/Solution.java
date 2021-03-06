package week5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Solution
{

    public static ArrayList<String> popularNFeatures(int numFeatures,
                                              int topFeatures,
                                              List<String> possibleFeatures,
                                              int numFeatureRequests,
                                              List<String> featureRequests) {

        ArrayList<String> result = new ArrayList<>();

        if (numFeatureRequests == 0 || numFeatures == 0) return new ArrayList<>();

        char[] specs = new char[]{',', '!', '?'};

        Map<String, Integer> possibleCounts = new HashMap<>();
        for (String request: featureRequests) {
            String[] keys = request.split(" ");
            for (String key: keys) {
                for (char c : specs) {
                    if (key.charAt(key.length()-1) == c) { // String method: charAt/indexOf
                        key = key.substring(0, key.length() - 1);
                    }
                }
                key = key.toLowerCase();
                if (possibleCounts.containsKey(key)) {
                    possibleCounts.put(key, possibleCounts.getOrDefault(key, 0) + 1);
                }
            }
        }

        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        for (Map.Entry<String, Integer> entry: possibleCounts.entrySet()) { // Forgot Generic casting
            pq.add(entry);
        }

        int i = 0;
        while (i < topFeatures && ! pq.isEmpty()) {
            Map.Entry<String, Integer> entry = pq.poll();
            if(! possibleFeatures.contains(entry.getKey())) { // forgot the getKey()
                continue;
            }
            result.add(entry.getKey());
            i++;
        }
        return result;
    }

    public static void main(String[] args) {
        int numFeatures = 6;
        int topFeatures = 2;
        List<String> possibleFeatures = Arrays.asList("storage", "battery", "hover", "alexa", "waterproof", "solar");

            int numFeatureRequests = 7;
        List<String> featureRequests = Arrays.asList(
                "wish my Kindle had even more storage!",
                "I wish the battery life on my Kindle lasted 2 years.",
                "I read in the bath and would enjoy waterproof Kindle.",
                "Waterproof and increased battery are my top two requests.",
                "I want to take my Kindle into the shower. Waterproof please waterproof!",
                "It would be neat if my Kindle would hover on my desk when not in use.",
                "Know cool would it be if my Kindle charged in the sun via solar power!"
        );
        List<String> result = popularNFeatures(numFeatures, topFeatures, possibleFeatures, numFeatureRequests, featureRequests) ;
        System.out.println(result);
    }


}
