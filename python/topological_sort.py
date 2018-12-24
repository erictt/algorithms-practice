#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Created on 2018-12-14 22:27:59
@author: Eric Yang
"""


from collections import defaultdict


class Digraph:

    def __init__(self, length):
        self.V = length
        self.vertices = defaultdict(list)
        self.visited = dict()
        self.stack = []
        self.first = None

    def addEdge(self, vertex1, vertex2):
        self.vertices[vertex1].append(vertex2)
        self.visited[vertex1] = False
        self.visited[vertex2] = False

    def topologicalSort(self):
        for k in self.visited:
            self.topologicalSortUtil(k)
        return self.stack

    def topologicalSortUtil(self, k):
        if not self.visited[k]:
            for v in self.vertices[k]:
                self.topologicalSortUtil(v)

            self.visited[k] = True
            self.stack.insert(0, k)


g = Digraph(6)
g.addEdge(5, 2)
g.addEdge(5, 0)
g.addEdge(4, 0)
g.addEdge(4, 1)
g.addEdge(2, 3)
g.addEdge(3, 1)

print(g.topologicalSort())
