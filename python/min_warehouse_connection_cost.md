**Problem Title:** Minimum Warehouse Connection Cost

**Difficulty:** Medium

**Problem Description:**

You are given a sorted (non-decreasing) array of integers `warehouseCapacity` of length *n*, representing the storage capacities of *n* warehouses. Warehouses are indexed from 0 to *n*-1.
There is always a distribution hub located at the last warehouse (index *n*-1). In addition, you receive a list of *queries*, `additionalHubs`. Each element in `additionalHubs` is a pair of integers, `[hubA, hubB]`, representing the indices of two *additional* distribution hubs (0 <= `hubA` < `hubB` < *n*-1).
Each warehouse *i* (0 <= *i* < *n*) must be connected to the *nearest* distribution hub whose index is greater than or equal to *i*. The cost of connecting warehouse *i* to hub *j* is calculated as `warehouseCapacity[j] - warehouseCapacity[i]`.
For *each* query `[hubA, hubB]` in `additionalHubs`, determine the *minimum* total connection cost for all warehouses.  Each query is *independent*; the presence of additional hubs in one query does not affect subsequent queries.

**Input:**
*   `warehouseCapacity`: A non-decreasing array of integers representing warehouse capacities.
*   `additionalHubs`: A list of pairs of integers, where each pair `[hubA, hubB]` represents the indices of two additional hubs for a query.

**Output:**
*   Return a list of integers. Each integer in the list represents the total minimum connection cost for the corresponding query in `additionalHubs`.

**Constraints:**
*   3 <= `warehouseCapacity.length` <= 2.5 * 10<sup>5</sup>
*   1 <= `queries.length` <= 2.5 * 10<sup>5</sup>
*   0 <= `warehouseCapacity[i]` <= 10<sup>9</sup>
*   `warehouseCapacity[i]` <= `warehouseCapacity[i+1]` for all 0 <= *i* < *n*-1
*   0 <= `additionalHubs[i][0]` < `additionalHubs[i][1]` < *n*-1

**Example 1:**

```
Input:
warehouseCapacity = [0, 2, 5, 9, 12, 18]
additionalHubs = [[1, 4], [0, 2]]

Output:
[12, 18]

Explanation:
Query 1 ([1, 4]): Hubs at indices 1, 4, and 5.
- Warehouse 0: Connects to hub 1 (cost: 2 - 0 = 2)
- Warehouse 1: Connects to hub 1 (cost: 2 - 2 = 0)
- Warehouse 2: Connects to hub 4 (cost: 12 - 5 = 7)
- Warehouse 3: Connects to hub 4 (cost: 12 - 9 = 3)
- Warehouse 4: Connects to hub 4 (cost: 12 - 12 = 0)
- Warehouse 5: Connects to hub 5 (cost: 18 - 18 = 0)
Total cost: 2 + 0 + 7 + 3 + 0 + 0 = 12

Query 2 ([0, 2]): Hubs at indices 0, 2, and 5.
- Warehouse 0: Connects to hub 0 (cost: 0 - 0 = 0)
- Warehouse 1: Connects to hub 2 (cost: 5 - 2 = 3)
- Warehouse 2: Connects to hub 2 (cost: 5 - 5 = 0)
- Warehouse 3: Connects to hub 5 (cost: 18 - 9 = 9)
- Warehouse 4: Connects to hub 5 (cost: 18 - 12 = 6)
- Warehouse 5: Connects to hub 5 (cost: 18 - 18 = 0)
Total Cost: 0 + 3+ 0+ 9 + 6+ 0= 18
```

**Example 2:**

```
Input:
warehouseCapacity = [2, 6, 8, 14]
additionalHubs = [[0, 1]]

Output:
[6]

Explanation:
Query 1 ([0, 1]): Hubs at indices 0, 1, and 3.
- Warehouse 0: Connects to hub 0 (cost: 2 - 2 = 0)
- Warehouse 1: Connects to hub 1 (cost: 6 - 6 = 0)
- Warehouse 2: Connects to hub 3 (cost: 14 - 8 = 6)
- Warehouse 3: Connects to hub 3 (cost: 14 - 14 = 0)
Total Cost: 0 + 0 + 6+ 0 = 6
```

**Follow-up:**

Can you solve the problem with a time complexity better than O(n\*q), n number of warehouses and q number of queries?
