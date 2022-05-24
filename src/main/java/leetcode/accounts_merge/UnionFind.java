package leetcode.accounts_merge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UnionFind {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, String> accountMap = new HashMap<>(); // email -> name
        Map<String, List<String>> emailMap = new HashMap<>(); // root email -> all emails belong to the same person
        Map<String, String> rootMap = new HashMap<>(); // email -> root email

        for (int i = 0; i < accounts.size(); i ++) {

            String name = accounts.get(i).get(0);
            String rootEmail = accounts.get(i).get(1);
            if (rootMap.containsKey(rootEmail)) {
                rootEmail = rootMap.get(rootEmail);
            } else {
                accountMap.put(rootEmail, name);
                emailMap.put(rootEmail, new ArrayList<>());
                emailMap.get(rootEmail).add(rootEmail);
                rootMap.put(rootEmail, rootEmail);
            }

            Set<String> rootEmails = new HashSet<>();

            for (int j = 2; j < accounts.get(i).size(); j++) {
                if (rootMap.containsKey(accounts.get(i).get(j))) {
                    rootEmails.add(rootMap.get(accounts.get(i).get(j)));
                } else {
                    rootMap.put(accounts.get(i).get(j), rootEmail);
                    emailMap.get(rootEmail).add(accounts.get(i).get(j));
                }
            }

            if (rootEmails.size() > 0) {
                merge(rootEmail, rootEmails, rootMap, emailMap);
            }
        }

        return buildUpResult(accountMap, emailMap);
    }

    private void merge(String rootEmail, Set<String> rootEmails, Map<String, String> rootMap, Map<String, List<String>> emailMap) {
        for (String parentEmail: rootEmails) {
            if (parentEmail.equals(rootEmail)) continue;
            for (String subEmail: emailMap.get(parentEmail)) {
                rootMap.put(subEmail, rootEmail);
                emailMap.get(rootEmail).add(subEmail);
            }
            emailMap.remove(parentEmail);
        }
    }

    private List<List<String>> buildUpResult(Map<String, String> accountMap, Map<String, List<String>> emailMap) {
        List<List<String>> res = new ArrayList<>();

        for (String rootEmail: emailMap.keySet()) {
            List<String> list = new ArrayList<>();
            list.add(accountMap.get(rootEmail));

            Collections.sort(emailMap.get(rootEmail));
            for (String email: emailMap.get(rootEmail)) list.add(email);
            res.add(list);
        }

        return res;
    }
}
