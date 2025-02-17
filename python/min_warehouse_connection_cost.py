from typing import List


class Solution:
    def minWarehouseConnectionCost(
        self, warehouseCapacity: List[int], additionalHubs: List[List[int]]
    ) -> List[int]:
        """
        Calculates the minimum total connection cost for each query.

        Args:
            warehouseCapacity: A non-decreasing list of warehouse capacities.
            additionalHubs: A list of pairs, each representing [hubA, hubB].

        Returns:
            A list of total connection costs, one for each query.
        """
        n = len(warehouseCapacity)
        results = []

        for hubs in additionalHubs:
            total_cost = 0
            hub_positions = sorted(hubs + [n - 1])  # Add the default hub (n-1) and sort

            for i in range(n):
                min_cost = float("inf")
                for hub_pos in hub_positions:
                    if hub_pos >= i:
                        cost = warehouseCapacity[hub_pos] - warehouseCapacity[i]
                        min_cost = min(min_cost, cost)  # Simplified logic.
                total_cost += min_cost
            results.append(total_cost)
        return results


# Example Usage (for local testing)
if __name__ == "__main__":
    solution = Solution()
    warehouseCapacity1 = [0, 2, 5, 9, 12, 18]
    additionalHubs1 = [[1, 4], [0, 2]]
    result1 = solution.minWarehouseConnectionCost(warehouseCapacity1, additionalHubs1)
    print(f"Example 1 Output: {result1}")  # Expected: [12, 18]

    warehouseCapacity2 = [2, 6, 8, 14]
    additionalHubs2 = [[0, 1]]
    result2 = solution.minWarehouseConnectionCost(warehouseCapacity2, additionalHubs2)
    print(f"Example 2 Output: {result2}")  # Expected: [6]

