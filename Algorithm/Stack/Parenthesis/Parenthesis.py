count = int(input())

def parenthesis(inputString):
    stack = []
    for i in inputString:
        if (i == "("):
            stack.append(i)
        elif (i == ")"):
            if (len(stack) != 0):
                stack.pop()
            else:
                return "NO"
    if (len(stack) > 0):
        return "NO"
    else:
        return "YES"

for n in range(count):
    inputString = input()
    print(parenthesis(inputString))