#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Dec 11 09:43:47 2018

@author: eric
"""


class Solution:
        # @param A : integer
        # @return a list of list of integers
    def prettyPrint(self, A):
        matrix = [[0 for i in range(2*A-1)] for j in range(2*A-1)]
        for i in range(1, A+1):
            self.initCircle(matrix, A, i)
        self.printMatrix(matrix)

    def initCircle(self, matrix, A, i):

        start = A-i  # 1:2
        end = start+i*2-1  # 1:3
        for x in range(start, end):
            matrix[start][x] = i
            matrix[end-1][x] = i
            matrix[x][start] = i
            matrix[x][end-1] = i

    def printMatrix(self, matrix):
        for i in range(len(matrix)):
            print(matrix[i])


Solution().prettyPrint(3)
