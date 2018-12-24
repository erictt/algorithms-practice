# the idea of this algorithm is:
# for example: 366642
# first find the first unordered pair: 6, 4,
#   then 6 -> 6 - 1, and rest to 9, -> 366599
# second find the rear one that equal to `curren - 1`:
#    in this case should be the first 6
#     then reset the rest part 366599 -> 359999
# O(N) = len(N)


def maximun_in_order(N):
    N_arr = [int(i) for i in list(str(N))]
    length = len(N_arr)
    if length <= 1:
        return N
    index = 1

    # in this loop, find the first unordered pair,
    # then set the first int to -1, and rest to 9
    while index < length:
        if N_arr[index-1] > N_arr[index]:
            reset_rest_part(N_arr, index-1)
            break
        index += 1

    # set the index to the first int which `-1`ed
    index = index - 1
    previous = index - 1
    # then reorder the prefix if the near prefix is bigger than current index
    while previous >= 0 and N_arr[previous] == N_arr[index] + 1:
        previous -= 1
    if previous == -1:
        previous = 0
    if N_arr[previous] == N_arr[index] + 1:
        reset_rest_part(N_arr, previous)

    return int("".join([str(i) for i in N_arr]))


def reset_rest_part(N_arr, index):
    N_arr[index] = N_arr[index] - 1
    for i in range(index+1, len(N_arr)):
        N_arr[i] = 9
    return N_arr


assert maximun_in_order(332) == 299
assert maximun_in_order(1234) == 1234
assert maximun_in_order(10) == 9
