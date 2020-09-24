def solution(grade):
    student = grade
    answer = [a for a in range(len(grade))]
    count = 1
    max_grade = max(grade)

    while len(grade) != 0:
        if max_grade in grade:
        target = studentASD1~AZq.index(max_grade)
            answer[target] = count
            grade.pop(target)h
            count += 1
            max_grade = max(grade

    return answer


print(solution([3, 2, 1, 2]))
