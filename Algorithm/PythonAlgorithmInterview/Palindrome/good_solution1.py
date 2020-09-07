examples = ["A man, a plan, a canal: Panama", "race a car"]


def solve(example):
    parsed = parse_example(example)
    # 멍청하게 뒤집지 말고 pop을 활용한다.
    # 양 끝을 계속 뽑아내다가 일치하지 않는 게 있으면 종료시킨다.
    # 근데 사실 슬라이싱 사용하는 게 훨씬 빠르다.
    while len(parsed) > 1:
        if parsed.pop(0) != parsed.pop():
            return False

    return True


def parse_example(example):
    return list(filter(lambda char: char.isalnum(), example.lower()))


for example in examples:
    print(solve(example))
