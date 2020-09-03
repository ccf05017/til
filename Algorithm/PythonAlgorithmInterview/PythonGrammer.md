# 파이썬 기본 정리
## 코딩 스타일
- PEP8을 준수하자
- 스네이크 케이스, 인덴트 4space
- 마무리할 때 타입힌트 활

## 리스트 컴프리헨션
- 함수형 패러다임의 map, filter를 대체 할 수 있다.
- 너무 남발하면 코드 읽기 어렵다. 적당히 쓰자
- 파이썬의 함수형 표현은 그닥 표현력이 좋지 않다.
```python
# 모두 결과가 같다
[(x + 1) for x in range(1, 11) if x % 2 == 1]

a = [];
for x in range(1, 11):
    if x % 2 == 1:
        a.append(x + 1)

list(
    map(lambda x: x + 1,
    list(
        filter(lambda x: x % 2 == 1,
        list(range(1, 11))))
    )
)
```

- 딕셔너리도 표현식을 사용할 수 있다.
```python
{ key: value for key, value in original.items() }
```

## 제너레이터
- yield 키워드를 통해 리턴처럼 결과를 반환하지만, 함수를 계속해서 진행하도록 할 수 있다.
- 이렇게 되면 적은 메모리로 반복적인 값 생성을 해낼 수 있다.
```python
def get_natural_number():
    count = 0
    while True:
        count += 1
        yield count
```
- 넥스트 함수에 제너레이터를 인수로 넘겨서 다음 값을 할당받을 수 있다.
```python
test_gen = get_natural_number()
print(next(test_gen))
```
- range 함수가 가장 대표적인 예시
    - range는 또한 인덱스로 원하는 순서의 값에 바로 접근할 수 있다.

## 열거형(enumerate)
- 순서가 있는 자료형을 인덱스를 포함한 enumerate 객체로 리턴한다.
```python
list_a = [1, 2, 3, 4, 5]
enumerate(list_a)
{ key: value for key, value in list(enumerate(list_a)) }
# => {0: 1, 1: 2, 2: 3, 3: 4, 4: 5}
```

## // 연산자
- 자료형을 유지하는 나눗셈 연산
- 자료형을 유지하기 위해 불필요하게 발생하는 숫자는 내림 처리한다.
```python
a = 5
b = 3

print(a / b) # 1.66666
print(a // b) # 1
```
- 몫과 나머지가 모두 필요할 땐 divmod 함수를 사용하면 좋다.
```python
divmod(5, 3) # 1, 2
```

## print 함수
- 코딩 테스트의 제한적인 환경에서 사용 가능한 디버깅 함수
- 기본 구분자는 공백이다.
```python
print('a', 'b')
# a b
```
- 구분자를 지정해줄 수 있다.
```python
print('a', 'b', sep=',')
# a,b
```
- 공백 구분자를 통해 줄바꿈을 없앨 수 있다.
```python
print('aa', end=' ')
print('bb')
# aa bb
```
- join 함수로 묶어서 출력할 수 있다.
```python
a = ['A', 'B']
print(' '.join(a))
# A B
```
- 포멧을 지정할 수 있다.
```python
# 모든 출력값은 2: Apple
idx = 1
fruit= "Apple"

print('{0}: {1}'.format(idx + 1, fruit))
print('{}: {}').format(idx + 1, fruit)
print(f'{idx + 1}: {fruit}') # 얘는 3.6 이상에서만 사용 가능하다.
```

## locals 함수
- 해당 클래스 내부의 모든 로컬 변수를 찾아서 딕셔너리 형태로 가져온다
- 디버깅에 유용하다.
```python
print(locals())
```
