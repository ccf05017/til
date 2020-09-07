inputs = ["A man, a plan, a canal: Panama", "race a car"]


def solve(inputs):
    for input in inputs:
        filtered = filter_string(input).lower()
        reverse_string = filtered[::-1]
        print(filtered == reverse_string)


def filter_string(input):
    filtered_input = filter(lambda char: char.isalnum(), input)
    return "".join(filtered_input)


solve(inputs)
