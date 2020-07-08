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
- 즉, 어떤 값이 들어오건 이터러블을 지원하면 객체로 바꿀 수 있다.

## 6. mapObject
- 주어진 이터러블에 식을 적용하고 객체로 반환하는 함수
- 함수형적인(이터러블적인) 사고 방식으로 생각하면 쉽게 구현할 수 있다.
```js
const mapObject = (f, obj) => _.go(
    obj,
    L.entries,                              // [['a', 1], ['b', 2], ['c', 3]]
    _.map(([k, v]) => [k, f(v)]),           // [['a', 11], ['b', 12], ['c', 13]]
    object
);

console.log(mapObject(a => a + 10, { a: 1, b: 2, c: 3}));
```

## 7. pick
- 객체 중 특정 키값의 자료형만 골라서 반환하는 함수
- 두개의 이터러블을 다루는 함수이기 때문에 상황 따라 유리한 방향으로 기준점을 잡아주는 게 좋다.
- 하지만 대체적으로는 뽑아낼 대상 이터러블이 더 적기 때문에 뽑아낼 대상을 기준점으로 잡는 게 보통 유리하다.
```js
const obj = { a: 1, b: 2, c: 3, d: 4, e: 5 };

const pick = (keys, obj) => _.go(
    keys,
    _.map(k => [[k], obj[k]]),
    _.object
);

console.log(pick(['b', 'c'], obj));
```

- 하지만 지금 상태에서 존재하지 않는 키로 pick하면 undefined 값이 할당된다.
- 이런 경우 undefiend를 아예 골라내는 게 좀더 안전하다.
```js
const pickOnlyExist = (keys, obj) => _.go(
    keys,
    _.map(k => [[k], obj[k]]),
    _.reject(([k, v]) => v === undefined),
    _.object
);

console.log(pickOnlyExist(['b', 'c', 'z'], obj));
```

## 8. indexBy
### 8.1 기본
- 어떤 값을 key, value 쌍으로 저장해놓는 함수
- reduce를 통해 구현
- 이터러블을 새로운 형태로 만들어 낼 때 -> 언제나 reduce 필요
```js
const users = [
    { id: 5, name: 'AA', age: 35 },
    { id: 10, name: 'BB', age: 26 },
    { id: 19, name: 'CC', age: 28 },
    { id: 23, name: 'DD', age: 34 },
    { id: 24, name: 'EE', age: 23 },
]

const indexBy = (f, itr) => 
    _.reduce((obj, a) => (obj[f(a)] = a, obj), {}, itr);

const users2 = indexBy(u => u.id, users);

console.log(users2);
```

### 8.2 filter까지 추가하기
- 기본적으로 indexBy의 결과는 이터러블이 아니기 때문에 filter 적용이 불가능하다.
- 하지만 index는 꼭 필요한 상황일 때 아래와 같은 방식으로 filter를 적용할 수 있다.
```js
_.go(
    users2,
    _.entries,
    _.filter(([_, { age }]) => age > 30),
    _.object,
    console.log
);
```