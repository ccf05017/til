# 표현력 높이기(함수를 값으로 다루자)
## 1. go (goExample.js)
### 1.1 기본 사용
- 가장 앞의 인자를 계속해서 뒤의 인자로 전달해주는 함수.
- reduce 동작과 같다.
- 즉시 값을 평가하는 데 사용된다.
- 복잡해 보이는 코드를 보기 좋게 변경시켜 준다.
```js
go = (...args) => this.reduce((a, f) => f(a), args);
```
### 1.2 응용(goExample2.js)
- 읽기 쉬운 코드를 작성할 수 있다.
```js
console.log("20000원 미만인 상품의 가격 총합 구하기 (original)");
console.log(
    rx.reduce(
        add,
        0,
        rx.map(p => p.price,
            rx.filter(p => p.price < 20000, sampleData.data))));

console.log("20000원 미만인 상품의 가격 총합 구하기 (go사용)");
rx.go(
    sampleData.data,
    products => rx.filter(p => p.price < 20000, products),
    products => rx.map(p => p.price, products),
    prices => rx.reduce(add, prices),
    console.log
)
```

## 2. pipe
- 함수를 리턴하는 함수
- 내부적으로 go 함수를 사용한다.
```js
pipe = (f, ...fs) => (...as) => this.go(f(...as), ...fs);
```

## 3. curry
### 3.1 기본 사용
- 함수를 받아서 함수를 리턴함.
- 원하는 만큼의 인자가 들어왔을 때 받아둔 함수로 나중에 평가시킴.
- 간단하게 인자가 두개 이상이 될 때까지 함수를 실행시키지 않고 붙들고 있는다(?)
```js
curry = f => 
    (a, ..._) => _.length ? f (a, ..._) : (..._) => f(a, ..._);
```

### 3.2 응용 (rxExample.js)
- 함수를 curry로 감싸면 기다리게 만들 수 있다. (평가 지연?)
```js
const multi = rx.curry((a, b) => a * b);

console.log(multi);         // function (평가 진행 안됨)
console.log(multi(2));      // function (평가 진행 안됨)
console.log(multi(2)(4));   // 8
```
- 이를 이용해서 고정된 동작을 수행하는 함수를 새로 만들 수 있다.
```js
const multi = rx.curry((a, b) => a * b);

const multi3 = multi(3);
console.log(multi3(2));     // 6
console.log(multi3(3));     // 9
console.log(multi3(4));     // 12
```

- 위에서 사용한 상품 예제를 읽기 쉽게 만들 수 있다.(대신 map, filter, reduce에 curry 적용해야 함.)
```js
rx.go(
    sampleData.data,
    products => rx.filter(p => p.price < 20000)(products),
    products => rx.map(p => p.price)(products),
    prices => rx.reduce(add)(prices),
    console.log
)
```

- '이게 뭐가 간단한데?' 싶다면 반복되는 부분을 제거할 수 있다.
```js
rx.go(
    sampleData.data,
    rx.filter(p => p.price < 20000),
    rx.map(p => p.price),
    rx.reduce(add),
    console.log
);
```