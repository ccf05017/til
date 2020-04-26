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

def test_answer():
    assert parenthesis("(())())") == "NO"
    assert parenthesis("(((()())()") == "NO"
    assert parenthesis("(()())((()))") == "YES"
    assert parenthesis("((()()(()))(((())))()") == "NO"
    assert parenthesis("()()()()(()()())()") == "YES"
    assert parenthesis("(()((())()(") == "NO"
