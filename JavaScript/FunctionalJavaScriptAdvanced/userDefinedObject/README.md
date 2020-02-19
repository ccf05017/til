# 사용자 정의 객체 다루기
- 사용자 정의 객체 또한 이터러블로 다룰 수 있다.

## 1. Map, Set
- 내장 객체지만, 실질적으로 사용자 정의 객체와 비슷하다.
- 다만 기본적으로 이터러블을 지원하기 때문에 다루기가 좀 더 쉽다.
- Map 예시(filter 적용)
```js
// 이터러블로 다루기
_.go(
    m,
    _.filter(([k, v]) => v % 2),
    _.takeAll,
    console.log
);

// 다시 Map으로 바꾸기
_.go(
    m,
    _.filter(([k, v]) => v % 2),
    entries => new Map(entries),
    console.log
);
```

-  Set 예시(reduce 사용)
```js
const s = new Set();
s.add(10);
s.add(20);
s.add(30);

const add = (a, b) => a + b;

console.log(_.reduce(add, s));
```
