#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Created on 2018-12-24 11:15:58
@author: Eric Yang
"""


def permutate_1(ls):
    result = [[]]
    for i in ls:
        L = len(result)
        for j in range(L):
            result.append(result[j] + [i])
    return result[1:]


def permutate_2(ls):
    L = len(ls)
    result = []
    for i in range(1, 2**L):
        temp = []
        index = 0
        while (i >> index) > 0 and index < L:
            if (i >> index) % 2 != 0:
                temp.append(ls[index])
            index += 1
        result.append(temp)
    return result


def permutate_3(ls, length):
    N = len(ls)

    if length > N:
        return None

    indices = [i for i in range(length)]

    result = []
    while indices[length-1] < N or indices[0] < N-length:

        # increase indices
        j = length-1
        while indices[length-1] >= N and j > 0:
            j -= 1
            indices[j] += 1
            if indices[j] > N-length+j:
                continue
            for x in range(j+1, length):
                indices[x] = indices[x-1]+1

        result.append([ls[i] for i in indices])

        indices[length-1] += 1

    return result

# ls = [1, 2, 3]
# print(permutate_2(ls))


ls = [1, 2, 3, 4, 5]
print(permutate_3(ls, 3))
