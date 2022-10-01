#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Dec 11 08:56:57 2018

@author: eric
"""


def levenshtein_distance(word1, word2):

    len1 = len(word1)
    len2 = len(word2)

    matrix = [[0 for i in range(len1 + 1)] for j in range(len2 + 1)]

    for i in range(len1 + 1):
        matrix[0][i] = i
    for i in range(len2 + 1):
        matrix[i][0] = i

    for i in range(1, len2 + 1):
        for j in range(1, len1 + 1):
            if word1[j - 1] == word2[i - 1]:
                matrix[i][j] = min(matrix[i - 1][j] + 1, matrix[i - 1][j - 1],
                                   matrix[i][j - 1] + 1)
            else:
                matrix[i][j] = min(matrix[i - 1][j] + 1,
                                   matrix[i - 1][j - 1] + 1,
                                   matrix[i][j - 1] + 1)
    print(matrix)
    return matrix[-1][-1]


print(levenshtein_distance("panapple", "apple"))
