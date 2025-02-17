def findMaxChainLength(s: str) -> int:
    """
    Finds the length of the longest substring satisfies these conditions:
        1. Length > 1: The substring must have at least two characters.
        2. Lexicographically Smaller Start: The first character of the substring must be alphabetically smaller than its last character.

    Args:
        s: The input string.

    Returns:
        The length of the longest valid substring, or 0 if none exists.
    """
    first_occur = [-1] * 26
    max_length = 0

    for i, char in enumerate(s):
        char_index = ord(char) - ord("a")  # convert character to index (0-25)

        if first_occur[char_index] == -1:
            first_occur[char_index] = i

        # check all chars less than the current one
        for j in range(char_index):
            if first_occur[j] != -1:
                max_length = max(max_length, i - first_occur[j] + 1)

    return max_length


# Test Cases (same as before)
print(findMaxChainLength("abcd"))  # Expected: 4
print(findMaxChainLength("fghbbadcba"))  # Expected: 5
print(findMaxChainLength("ecbdca"))  # Expected: 3
print(findMaxChainLength("aa"))  # Expected: 0
print(findMaxChainLength("ba"))  # Expected: 0
print(findMaxChainLength("ab"))  # Expected: 2
