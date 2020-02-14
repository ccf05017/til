# 객체를 함수형에서 다루기
- 여기서 객체는 key, value 쌍을 의미
- 이것들을 함수형으로 다루면 지연 평가, 동시성의 이점을 모두 누릴 수 있다.

## 1. values
- 객체의 값으로만 무언가 다룰 때 사용할 수 있다.
- 자바스크립트는 기본적으로 Object.values 함수 사용
- Object.values는 기본적으로 즉시 평가된다.
```js
const obj = {
    a: 1,
    b: 2,
    c: 3,
    d: 4
};

_.go(
    obj,
    Object.values,
    _.reduce((a, b) => a + b),
    console.log
);
```

- FxJS는 L.values를 통해 같은 효과를 얻을 수 있다.
```js
L.values = function *(obj) {
    for (const k in obj) {
        yield obj[k];
    }
}
```

- 지연 평가의 이점을 모두 사용 가능하다. (필요한 만큼만 효율적으로 연산)
- 주어진 값이 복잡할수록 효율적인 효과를 낼 수 있다.
- 아래와 같은 상황에서 필요한 딱 두개만큼만 연산을 진행한다.
```js
_.go(
    obj,
    L.values,
    L.map(a => a + 10),
    L.take(2),
    _.reduce((a, b) => a + b),
    console.log
);
```