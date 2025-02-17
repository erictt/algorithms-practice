"""
You are given a string of n characters s[1 . . . n],
which you believe to be a corrupted text document in which all punctuation has vanished (so that it looks something like “itwasthebestoftimes...”).
You wish to reconstruct the document using a dictionary, which is available in the form of a Boolean function dict(·): for any string w,

dict(w) is True if w is a valid word and False otherwise.

(a) Give a dynamic programming algorithm that determines whether the string s[·] can be reconstituted as a sequence of valid words. The running time should be at most O(n 2 ), assuming calls to dict take unit time.
(b) In the event that the string is valid, make your algorithm output the corresponding sequence of words.
"""

str = "itwasthebestoftimes"

dict = set(["it", "was", "the", "best", "of", "times"])

"""

"""

i = 0  # indicate the split
j = 0  # the 0...j substring we looked up

metrics = [[False] * len(str)] * len(str)

print(metrics)

for j in range(len(str)):
    for i in range(j):
        if metrics[i][j] is False:
            if str[i:j] in dict:
                metrics[i][j] = True
            else:
                for k in range(i, j):
                    if metrics[i][k] and metrics[k][j]:
                        metrics[i][j] = True
                        break
