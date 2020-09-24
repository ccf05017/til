def solution(s1, s2):
    dup1_count = 0
    dup2_count = 0

    target1 = list(s1)
    target2 = list(s2)
    target3 = list(s1)
    target4 = list(s2)

    min_length = min(len(s1), len(s2))

    for _ in range(min_length):
        if target1.pop(0) == target2.pop(len(target2) - 1):
            dup1_count += 1
        else:
            break

    for _ in range(min_length):
        if target3.pop(len(target3) - 1) == target4.pop(0):
            dup2_count += 1
        else:
            break

    if dup1_count == dup2_count:
        candidates = [s2 + s1[dup2_count::], s1 + s2[dup1_count::]]
        candidates.sort()
        return candidates[0]
    if dup1_count < dup2_count:
        return s2 + s1[dup2_count::]
    if dup1_count > dup2_count:
        return s1 + s2[dup1_count::]

    answer = ''
    return answer


print(solution("xyZA", "ABCxy"))
print(solution("ABCxy", "xyZA"))
print(solution("AxA", "AyA"))
