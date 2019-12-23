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
