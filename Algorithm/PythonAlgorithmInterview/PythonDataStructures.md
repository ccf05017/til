# 파이썬 내장 자료형
- 파이썬은 전부다 객체다
- None
- 숫자
    - 정수형
        - 정수(int)
        - 불리언(boolean): 정수형의 하부 클래스이기 때문에 정수와 비교가 가능하다.
    - 실수(float)
- 집합형
    - 집합(set)
- 매핑
    - 딕셔너리(dict)
- 시퀀스: 순서가 있는 값의 나
    - 불변
        - 문자열(string)
        - 튜플(tuple)
        - 바이트(bytes)
    - 가변
        - 리스트(list)
## '==' 과 is
- 둘 다 동등 비교를 수행한다.
- ==: 값 비교
- is: 레퍼런스 비교
- None은 값이 없기 때문에 is로만 비교가 가능하다.

## list
- 동적 배열이며, 범용 자료형이다.
- 여러 자료형을 한 list에서 한꺼번에 다룰 수 있다.
    - 배열과 연결 리스트가 짬뽕된 느낌의 클래스
- 스택이건 배열이건 가리지 않고 이거 갖다 쓰면 된다.
- 스택 연산은 대부분 효율적이지만 큐 연산은 느린 경우가 있기 때문에 주의해서 사용해야 한다.
- 각종 메서드
    - append({value}): 가장 끝에 인자로 전달된 값을 추가시킨다.
    - insert({idx}, {value}): 특정 위치의 인덱스를 지정해서 요소를 추가할 수 있다.
    - [{startIdx}:{endIdx}:{step}]: 슬라이싱으로 배열을 자를 수 있다. 문자열에서도 사용 가능하다. (마지막 인덱스는 포함되지 않는다.)
    - del({idx}): 해당 인덱스의 값을 삭제한다.
        - 얘는 함수가 아니라 키워드다. 사용법이 조금 다르다
            ```python
            test = [1, 2, 3, 4, 5]
            del test[0]
            print(test) # [2, 3, 4, 5]
            ```
    - remove({value}): 해당 값을 찾아서 삭제한다.
    - pop({idx}): 해당 인덱스의 값을 리턴하고 리스트에서 삭제한다.
    
## dict
- 해시 테이블로 구현된 key, value 쌍 자료형
- 다양한 타입을 키로 사용할 수 있다.(불변 객체도 키로 사용할 수 있다.)
- 찾고 삭제하는 데 아주 뛰어난 성능을 보여준다. 거의 대부분의 연산이 O(1)이다.
- 주의 사항으로 3.7 이상이 아니면 dict 자료형은 순서를 유지하지 않는다.
- 기본 사용법
    ```python
    a = dict()
    b = {}
    
    a['test'] = 'hello'
    b['test2'] = 'hello2'
    
    c = { 'key1': 'value1', 'key2': 'value2' }
    ```
- 존재하지 않는 키를 조회 시 에러가 발생한다.
    - try, exception 구문으로 잡을 수 있다.
    - 삭제할 때도 마찬가지로 발생한다.
- in 구문을 통해 키가 있는지 확인할 수 있다.
    ```python
    a = { 'key1': 'value1' }
    
    print('key1' in a) # True
    print('key3' in a) # False
    ```
- items 함수로 이터러블 전환이 가능하다.
    ```python
    a = { 'key1': 'value1', 'key2': 'value2' }
    
    for key, value in a.items():
        print(f'{key}: {value}')
    ```
- 딕셔너리 모듈
    - defaultDict: 존재하지 않는 키에 대해 기본값로 연산을 진행한다.
        ```python
        a = collections.defaultDict(int)
        
        a['A'] = 1
        a['B'] = 2
        a['C'] += 3
        
        print(a) # { 'A': 1, 'B': 2, 'C': 3 }
        # int 기본값 0에 3을 더한 값이 C에 할당됐다.
        ```
     - Counter: 아이템에 대한 개수를 개산해서 딕셔너리로 리턴한다.
        ```python
        a = [1, 2, 3, 4, 5, 5, 5, 6, 6]
        b = collections.Counter(a)
        b # Counter({5: 3, 6: 2, 1: 1, 2: 1, 3: 1, 4: 1})
        ```
       - most_common({int}) 메서드를 통해 가장 빈도 수가 높은 요소를 추출해 낼 수 있다.
