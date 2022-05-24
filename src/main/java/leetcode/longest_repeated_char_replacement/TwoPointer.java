package leetcode.longest_repeated_char_replacement;

public class TwoPointer {
    public int characterReplacement(String s, int k) {

        int[] count = new int[26];

        int mostFreq = 0;
        int max = k;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            int i = index(s, right);
            count[i] ++;
            mostFreq = Math.max(mostFreq, count[i]);

            // distance between left and right = right - left + 1
            // i was confused about why mostfreq didn't change after left ++; here is why:
            // left is either equal to right or not.
            // if left = right, then
            //      if right became mostfreq after the count[] ++, then right >= the second freq + 1
            //          then after left ++, right should still be the mostfreq
            //      if right is not the mostfreq, then left++ won't change mostfreq at all
            // if left != right
            //      if right became mostfreq after the count[] ++
            //          then left ++ won't change mostfreq
            //      if right is not mostfreq, and left is not as well
            //          save as above, mostfreq won't change
            //      if right is not mostfreq, but left is
            //          there are two possibles:
            //              1. left's freq is the biggest and none of the others have the same freq,
            //                  so left ++ won't change the fact that left is still the most freq one.
            //              2. there is another i has the same amount of freq as left,
            //                  after left ++, the mostfreq should be changed to another i.
            //                  and yes, it should be, but the volume of mostfreq is still the same.
            //                  because, we're not tracking which i is the most freq, but the volume of most freq.

            if (right - left + 1 - mostFreq > k) {
                count[index(s, left)] --;
                left ++;
            }

            max = Math.max(max, right - left + 1);
        }

        return max;
    }

    private int index(String s, int i) {
        return s.charAt(i) - 'A';
    }

//     public int characterReplacement(String s, int k) {
//         // case 1:
//         // k == s.length() return s.length();

//         // case 2:
//         // no repeated chars:
//         // return k + 1;

//         // case 3:
//         // has two repeated chars
//         // case 3.1:
//         // the distance of two identical chars is less than or equal k, return k + 2
//         // case 3.2:
//         // > k return k + 1;

//         // case 4:
//         // has more than two repeated chars
//         // use array to store all existing repeated chars' indices, and find the leftmost that bigger that i - k + nums of the right


//         if (k >= s.length() - 1) return k;

//         int max = k + 1;

//         List<Integer>[] indices = new ArrayList[26];
//         for (int i = 0; i < 26; i++) {
//             indices[i] = new ArrayList<Integer>();
//         }


//         for (int i = 0; i < s.length(); i++) {
//             int index = s.charAt(i) - 'A';
//             indices[index].add(i);
//             if (indices[index].size() == 2) {
//                 if (indices[index].get(1) - indices[index].get(0) - 1 <= k)
//                     max = Math.max(max, 2 + k);
//                 else
//                     indices[index].remove(0);
//             } else if (indices[index].size() > 2) {
//                 max = Math.max(max, Math.min(findLongestInArray(indices[index], k), s.length()));
//             }
//         }

//         return max;
//     }

//     // find the smallest index that can fit in k
//     private int findLongestInArray(List<Integer> list, int k) {
//         int j = list.get(list.size() - 1);
//         for (int i = 0; i < list.size() - 1; i++) {
//             // how many elements between i and last element
//             int count = list.size() - i - 2;
//             if (j - list.get(i) - 1 <= (k + count)) {
//                 return k + count + 2;
//             }
//         }
//         return -1;
//     }
}
