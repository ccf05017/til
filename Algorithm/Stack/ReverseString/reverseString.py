inputString = input()

stack = []
result = ''
for char in inputString:
    if char == ' ':
        stack.reverse()
        reversedString = ''.join(stack)
        result += reversedString
        result += ' '
        stack.clear()
    else:
        stack.append(char)

print(result[:-1])
