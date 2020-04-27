iterNumber = int(input())

def solution():
    stack = []
    count = 0
    result = ""

    for _ in range(iterNumber):
        target = int(input())

        if (target > count):
            while(target > count):
                count += 1
                stack.append(count)
                result = result + "+\n"
            stack.pop()
            result = result + "-\n"

        else:
            if (stack[-1] != target):
                return "NO"
            stack.pop()
            result = result + "-\n"
            
    return result

print(solution())
