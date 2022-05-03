package leetcode.merge_two_sorted_arrays;

public class Solution {
    private class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        ListNode dummy = new ListNode(0);

        ListNode cur = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val > list2.val) {
                cur.next = list2;
                list2 = list2.next;
            } else {
                cur.next = list1;
                list1 = list1.next;
            }
            cur = cur.next;
        }

        cur.next = list1 == null? list2 : list1;

        return dummy.next;
    }
//     public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

//         if (list1 == null) return list2;
//         if (list2 == null) return list1;

//         if (list1.val > list2.val) {
//             list2.next = mergeTwoLists(list1, list2.next);
//             return list2;
//         }

//         list1.next = mergeTwoLists(list1.next, list2);
//         return list1;
//     }
}
