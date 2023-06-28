class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> newIntervals = new ArrayList<>();

        boolean addedNew = false;
        for (int i = 0; i < intervals.length; i++) {
            // new interval on the left
            if (newInterval[1] < intervals[i][0]) {
                appendAll(newIntervals, newInterval, intervals, i);
                addedNew = true;
                break; // 1. don't forget to break
            // new interval on the right
            } else if (newInterval[0] > intervals[i][1]) {
                newIntervals.add(intervals[i]);
            // new interval overlapped with ith interval
            } else {
                newInterval[0] = Math.min(intervals[i][0], newInterval[0]);
                newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            }
        }

        // don't forget to add the new interval if it's not added yet
        if (!addedNew) {
            newIntervals.add(newInterval);
        }

        return newIntervals.toArray(new int[newIntervals.size()][2]);
    }

    private void appendAll(List<int[]> newIntervals, int[] newInterval, int[][] intervals, int start) {
        newIntervals.add(newInterval);
        for (int i = start; i < intervals.length; i++) {
            newIntervals.add(intervals[i]);
        }
    }
}
