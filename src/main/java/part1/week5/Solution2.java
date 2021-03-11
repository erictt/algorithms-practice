package part1.week5;
// IMPORT LIBRARY PACKAGES NEEDED BY YOUR PROGRAM
// SOME CLASSES WITHIN A PACKAGE MAY BE RESTRICTED
// DEFINE ANY CLASS AND METHOD NEEDED
// CLASS BEGINS, THIS CLASS IS REQUIRED
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Solution2 {

    private static class PairString {
        String first;
        String second;

        public PairString(String first, String second) {
            this.first = first;
            this.second = second;
        }
    }

    // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    // RETURN "null" IF NO ITEM ASSOCIATION IS GIVEN
    static List<String> largestItemAssociation(List<PairString> itemAssociation) {

        List<String> longest = new LinkedList<>();

        LinkedList<String> current = new LinkedList<>();
        for (PairString ps: itemAssociation) {
            if (! current.isEmpty()) {
                if (ps.first != current.getLast()) {
                    if (current.size() > longest.size()) {
                        longest = current;
                    }
                    current = new LinkedList<>();
                    current.add(ps.first);
                }
            } else {
                current.add(ps.first);
            }
            current.add(ps.second);
        }

        if (current.size() > longest.size()) {
            longest = current;
        }

        return longest;
    }

    public static void main(String[] args) {
        List<PairString> itemAssociation  = new ArrayList<>();
        itemAssociation.add(new PairString("item1", "item2"));
        itemAssociation.add(new PairString("item3", "item4"));
        itemAssociation.add(new PairString("item4", "item5"));
        List<String> result = largestItemAssociation(itemAssociation);

        System.out.println(result);
    }
}
