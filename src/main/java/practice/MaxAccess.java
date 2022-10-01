package practice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
Suppose we have an unsorted log file of accesses to web resources. Each log entry consists of an access time, the ID of the user making the access, and the resource ID.
The access time is represented as seconds since 00:00:00, and all times are assumed to be in the same day.
Example:
logs1 = [
["58523", "user_1", "resource_1"],
["62314", "user_2", "resource_2"],
["54001", "user_1", "resource_3"],
["200", "user_6", "resource_5"],
["215", "user_6", "resource_4"],
["54060", "user_2", "resource_3"],
["53760", "user_3", "resource_3"],
["58522", "user_22", "resource_1"],
["53651", "user_5", "resource_3"],
["2", "user_6", "resource_1"],
["100", "user_6", "resource_6"],
["400", "user_7", "resource_2"],
["100", "user_8", "resource_6"],
["54359", "user_1", "resource_3"],
]
Write a function that takes the logs and returns the resource with the highest number of accesses in any 5 minute window, together with how many accesses it saw.

Expected Output:
most_requested_resource(logs1) # => ('resource_3', 3)
Reason: resource_3 is accessed at 53760, 54001, and 54060
 */

class MaxAccess {

    private static final int TIME_RANGE = 300; // seconds
    public Node searchMaxAccessesResource(String[][] logs) {

        if (logs.length == 0) return null;
        if (logs.length == 1) return new Node(logs[0][2], 1);

        Arrays.sort(logs, (a, b) -> Integer.valueOf(a[0]) - Integer.valueOf(b[0]));

        Map<String, Integer> freqMap = new HashMap<>();

        String maxResource = logs[0][2];
        int maxAccess = 1;
        int startIndex = 0;
        freqMap.put(logs[0][2], 1);

        int i = 1;
        while (i < logs.length) {
            int time = Integer.valueOf(logs[i][0]);
            while (time - Integer.valueOf(logs[startIndex][0]) > TIME_RANGE) {
                freqMap.put(logs[startIndex][2], freqMap.get(logs[startIndex][2]) - 1);
                startIndex++;
            }
            if (! freqMap.containsKey(logs[i][2])) {
                freqMap.put(logs[i][2], 1);
            } else {
                freqMap.put(logs[i][2], freqMap.get(logs[i][2]) + 1);
            }
            if (maxAccess <= freqMap.get(logs[i][2])) {
                maxAccess = freqMap.get(logs[i][2]);
                maxResource = logs[i][2];
            }
            i++;
        }

        return new Node(maxResource, maxAccess);
    }

    private class Node {
        String resourceName;
        int maxAccess;

        Node (String resourceName, int maxAccess) {
            this.resourceName = resourceName;
            this.maxAccess = maxAccess;
        }
    }

    public static void main(String[] main) {
        MaxAccess s = new MaxAccess();
        String[][] logs1 = {
                {"58523", "user_1", "resource_1"},
                {"62314", "user_2", "resource_2"},
                {"54001", "user_1", "resource_3"},
                {"200", "user_6", "resource_5"},
                {"215", "user_6", "resource_4"},
                {"54060", "user_2", "resource_3"},
                {"53760", "user_3", "resource_3"},
                {"58522", "user_22", "resource_1"},
                {"53651", "user_5", "resource_3"},
                {"2", "user_6", "resource_1"},
                {"100", "user_6", "resource_6"},
                {"400", "user_7", "resource_2"},
                {"100", "user_8", "resource_6"},
                {"54359", "user_1", "resource_3"}
        };

        String[][] logs2 = {
                {"300", "user_1", "resource_3"},
                {"599", "user_1", "resource_3"},
                {"900", "user_1", "resource_3"},
                {"1199", "user_1", "resource_3"},
                {"1200", "user_1", "resource_3"},
                {"1201", "user_1", "resource_3"},
                {"1202", "user_1", "resource_3"}
        };

        String[][] logs3 = {
                {"300", "user_10", "resource_5"}
        };
        Node node = s.searchMaxAccessesResource(logs1);
        System.out.println(node.resourceName + ": " + node.maxAccess);
    }
}
