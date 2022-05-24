package leetcode.accounts_merge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// https://leetcode.com/problems/accounts-merge/
public class DFS {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        Map<String, String> accountNameMap = new HashMap<>(); // email -> name

        Map<String, Set<String>> adjs = new HashMap<>(); // email -> adj emails

        // build up edges
        for (List<String> account: accounts) {
            String accountName = account.get(0);
            accountNameMap.put(account.get(1), accountName);
            for (int i = 2; i < account.size(); i++) {
                accountNameMap.put(account.get(1), accountName);
                if (! adjs.containsKey(account.get(i-1)))
                    adjs.put(account.get(i-1), new HashSet<>());
                if (! adjs.containsKey(account.get(i)))
                    adjs.put(account.get(i), new HashSet<>());
                adjs.get(account.get(i-1)).add(account.get(i));
                adjs.get(account.get(i)).add(account.get(i-1));
            }
        }

        Set<String> visited = new HashSet<>(); // email visited or not in the dfs
        // run dfs to put all adjecent vertices into single list;
        List<List<String>> res = new ArrayList<>();
        for (String email: accountNameMap.keySet()) {
            if (visited.contains(email)) continue;
            List<String> list = new ArrayList<>();
            dfs(email, visited, adjs, list);
            Collections.sort(list);
            list.add(0, accountNameMap.get(email));
            res.add(list);
        }
        return res;
    }

    private void dfs(String email, Set<String> visited, Map<String, Set<String>> adjs, List<String> list) {
        list.add(email);
        visited.add(email);
        if (! adjs.containsKey(email) || adjs.get(email).size() == 0) return;
        for (String adjEmail: adjs.get(email)) {
            if (! visited.contains(adjEmail)) {
                dfs(adjEmail, visited, adjs, list);
            }
        }
    }
}
