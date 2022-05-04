package leetcode.trapping_rain;

import java.util.Stack;

// unlike the other two solutions, this one sum up the water block horizontally.
// It uses a stack to keep all the bar that lower than previous one.
// when encounter one that bigger than previous one, calculate the trapping area horizontally.
// e.g. [4, 2, 0, 3, 2, 5]
// index[0, 1, 2, 3, 4, 5]
// the first one that bigger than previous is 3, then pop two previous elements from stack, 2 and 0.
// then add min(2, 3) * (3 - 1 - 1). // the width between two indices.
// then add min(4, 3) * (3 - 0 - 1),
// then add 3 into the stack which became [0, 3] because 0 is higher than 3, so we kept it in the stack
public class Stacks {
    public int trap(int[] height) {

        int sum = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        for (int i = 1; i < height.length; i++) {
            if (stack.isEmpty() || height[stack.peek()] >= height[i]) {
                stack.push(i);
                continue;
            }

            int bar = stack.pop();
            while (! stack.isEmpty()) {
                int pre = stack.peek();
                if (height[pre] <= height[i]) {
                    sum += (height[pre] - height[bar]) * (i - pre - 1);
                    bar = stack.pop();
                } else {
                    sum += (height[i] - height[bar]) * (i - pre - 1);
                    break;
                }
            }

            stack.push(i);
        }

        return sum;
    }
}
