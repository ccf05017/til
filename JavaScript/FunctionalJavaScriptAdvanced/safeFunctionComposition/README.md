# 안전한 함수 합성
## map + 배열 모나드로 안전하게 합성하자.
- 아래와 같이 여러개의 함수 효과를 중복해서 적용하는 걸 함수 합성이라 한다.
```js
const f = x => x + 10;
const g = x => x - 5;
const fg = x => f(g(x));

console.log(fg(10)); // 15
```

- 문제는 이런 상황에서 예외가 발생할 때 안전하지 못할 수 있다.
```js
console.log(fg());  // NaN
```

- 하지만 map과 배열 모나드를 사용하면 안전한 함수 합성을 할 수 있다.
```js
const f = x => x + 10;
const g = x => x - 5;
const fg = x => f(g(x));

// 정상적인 경우
_.go(
    [10],
    L.map(fg),
    _.each(console.log)
);

// 비정상적인 경우 -> 아예 연산이 실행되지 않는다.
_.go(
    [],
    L.map(fg),
    _.each(console.log)
);
```

## find 대신 L.filter를 쓰자(+take)
- find 함수는 예외 상황(없다던가)에 대한 대처가 없다.
- 이걸 방지하려면 매번 if문을 걸어줘야 한다.
```js
const users = [
    { name: 'AA', age: 35 },
    { name: 'BB', age: 25 },
    { name: 'CC', age: 15 },
    { name: 'DD', age: 5 },
    { name: 'EE', age: 45 },
]

const user = _.find(u => u.name == 'FF', users);
console.log(user);      // undefiend
```

- 하지만 지연 처리 된 filter를 사용하면 해결 가능하다.
- 지연 처리 filter는 자신과 맞는 조건이 아니면 실행 자체가 안되기 때문이다.
```js
_.go(
    users,
    L.filter(u => u.name == "FF"),
    L.take(1),
    _.each(console.log)
);
```
