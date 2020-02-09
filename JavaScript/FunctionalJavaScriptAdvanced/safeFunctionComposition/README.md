# 안전한 함수 합성
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
