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
            hubs_index = [i - 1 for i in hubs]
            total_cost = 0

            for i in range(n):
                if i < hubs_index[0]:
                    total_cost += (
                        warehouseCapacity[hubs_index[0]] - warehouseCapacity[i]
                    )
                elif hubs_index[0] < i < hubs_index[1]:
                    total_cost += (
                        warehouseCapacity[hubs_index[1]] - warehouseCapacity[i]
                    )
                elif i > hubs_index[1]:
                    total_cost += warehouseCapacity[n - 1] - warehouseCapacity[i]
                    # total_cost += min(
                    #     warehouseCapacity[i] - warehouseCapacity[hubs_index[1]],
                    #     warehouseCapacity[n - 1] - warehouseCapacity[i],
                    # )
                # elif hubs_index[0] < i < hubs_index[1]:
                #     total_cost += min(
                #         warehouseCapacity[i] - warehouseCapacity[hubs_index[0]],
                #         warehouseCapacity[hubs_index[1]] - warehouseCapacity[i],
                #     )
            results.append(total_cost)
        return results


# Example Usage (for local testing)
if __name__ == "__main__":
    solution = Solution()
    warehouseCapacity1 = [0, 2, 5, 9, 12, 18]
    additionalHubs1 = [[2, 5], [1, 3]]
    result1 = solution.minWarehouseConnectionCost(warehouseCapacity1, additionalHubs1)
    print(f"Example 1 Output: {result1}")  # Expected: [12, 18]

    warehouseCapacity2 = [2, 6, 8, 14]
    additionalHubs2 = [[1, 2]]
    result2 = solution.minWarehouseConnectionCost(warehouseCapacity2, additionalHubs2)
    print(f"Example 2 Output: {result2}")  # Expected: [6]
