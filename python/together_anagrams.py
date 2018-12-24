#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Dec 11 11:20:32 2018

@author: eric
"""

"""
Given a sequence of words, print all anagrams together
"""


class Word:
    def __init__(self, word, index):
        self.word = word
        self.index = index
        self.word = self.sort()

    def sort(self):
        return "".join(sorted(self.word))


def printAnagramsTogether(words):
    copies = [Word(words[i], i) for i in range(len(words))]

    copies = sorted(copies, key=lambda word: word.word)

    for word in copies:
        print(words[word.index])


words = ["cat", "dog", "tac", "god", "act"]
printAnagramsTogether(words)
