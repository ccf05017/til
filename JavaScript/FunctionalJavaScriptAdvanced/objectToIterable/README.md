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

## 2. entries
- 객체들을 다룬다.
- 위의 예제와 거의 유사하다.
```js
L.entires = function *(obj) {
    for (const k in obj) {
        yield [k, obj[k]];
    }
}
```

- 위의 두 예제를 통해 어떤 값이든 제너레이터를 통해 이터러블로 바꾸고, 이터러블 프로그래밍을 할 수 있음을 알 수 있다.

## 3. keys
- 위의 예제들과 동일한 방식
- 키만 뽑아내는 동작을 담당한다.
```js
L.entires = function *(obj) {
    for (const k in obj) {
        yield k;
    }
}
```

## 4. 어떤 값이던 이터러블로 다뤄보자
- 어떤 제너레이터든 이터레이터로 만든 뒤 이터러블 프로그래밍을 할 수 있다.
- 제너레이터는 어떤 이터러블 프로그래밍에서나 사용할 수 있는 값이다.
- 즉, 모든 '문장(코드, 상황)'을 전달할 수 있다는 의미
- 전에도 얘기했지만, 상황 그 자체를 값으로 사용할 수 있다.

## 5. object 함수
- 배열로 이루어진 값을 객체로 바꿔주는 함수
- 기존 방식을 활용하면 아래와 같이 구현할 수 있다.
```js
const input = [['a', 1], ['b', 2], ['c', 3]];

const object = entries => _.go(
    entries,
    L.map(([k, v]) => ({ [k]: v })),
    _.reduce(Object.assign)
);

console.log(object(input));
```

- 자바스크립트 내장 Map과 같은 자료 타입을 다룰 때 아주 유용하다.
- 자바스크립트 내장 Map은 바로 JSON으로 바꿀 수 없지만 이터러블을 지원하기 때문에 object 함수 활용 가능하다.
