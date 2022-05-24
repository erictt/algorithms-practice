package leetcode.LFU;

import java.util.HashMap;
import java.util.Map;


// problem: https://leetcode.com/problems/lfu-cache/
// refer to this solution: https://leetcode.com/problems/lfu-cache/discuss/513157/Java-O(1)-16-ms-(beating-97)-using-2-HashMaps-and-DoublyLinkedList-with-detailed-explanations
public class DoublyLinkedList {

    private int capacity;
    private Map<Integer, LinkedNode> kvMap;
    private Map<Integer, LinkedNodeList> countGroupMap;
    private int minFreq = 1;

    public DoublyLinkedList(int capacity) {
        this.capacity = capacity;
        kvMap = new HashMap<>();
        countGroupMap = new HashMap<>();
    }

    public int get(int key) {
        if (! kvMap.containsKey(key)) return -1;
        LinkedNode node = kvMap.get(key);
        increaseCount(node);
        return kvMap.get(key).value;
    }

    public void put(int key, int value) {
        if (kvMap.containsKey(key)) {
            kvMap.get(key).value = value;
            increaseCount(kvMap.get(key));
        } else {
            if (capacity == 0) return;
            if (kvMap.size() == capacity) {
                removeLeastFreqOne();
            }
            kvMap.put(key, new LinkedNode(key, value));
            increaseCount(kvMap.get(key));
            minFreq = 1;
        }
    }

    // increase the count in kCountMap and move it in countGroupMap
    private void increaseCount(LinkedNode node) {
        if (node.freq == 0) {
            node.freq++;
            if (countGroupMap.get(1) == null) countGroupMap.put(1, new LinkedNodeList());
            countGroupMap.get(1).append(node);
        } else {
            countGroupMap.get(node.freq).removeNode(node);
            if (countGroupMap.get(node.freq).count == 0 && minFreq == node.freq) minFreq++; // forgot to add condition minFreq == node.freq
            node.freq++;
            if (countGroupMap.get(node.freq) == null) countGroupMap.put(node.freq, new LinkedNodeList());
            countGroupMap.get(node.freq).append(node);
        }
    }

    private void removeLeastFreqOne() {
        // LinkedNode node = null;
        // for (int count: countGroupMap.keySet()) {
        //     if (countGroupMap.get(count).count != 0) {
        //         node = countGroupMap.get(count).removeFirst();
        //         break;
        //     }
        // }
        LinkedNode node = countGroupMap.get(minFreq).removeFirst();
        if (node == null) return;
        kvMap.remove(node.key);
    }

    private class LinkedNode {
        int key;
        int value;
        int freq;
        LinkedNode next;
        LinkedNode prev;

        LinkedNode(int key, int value) {
            this.key = key;
            this.value = value;
            freq = 0;
        }
    }

    private class LinkedNodeList {
        LinkedNode first;
        LinkedNode last;
        int count = 0;

        void append(LinkedNode node) {
            if (first == null) {
                first = node;
                last = node;
            } else {
                node.prev = last;
                last.next = node;
                last = node;
            }
            count++;
        }

        LinkedNode removeFirst() {
            if (first == null) return null;
            LinkedNode temp = first;
            first = first.next;
            if (first == null) last = null;
            count--;
            return temp;
        }

        void removeNode(LinkedNode node) {

            if (first == node && node == last) {
                first = null;
                last = null;
            } else if (node == first) {
                first = node.next;
                first.prev = null;
            } else if (node == last) {
                last = node.prev;
                last.next = null;
            } else {
                node.next.prev = node.prev;
                node.prev.next = node.next;
            }

            count--;
        }
    }

}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
