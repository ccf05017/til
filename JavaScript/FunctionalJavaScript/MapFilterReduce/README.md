# 함수형 프로그래밍의 기본 함수들
## (첨부) 예시 데이터
```js
const products = [
    { name: '반팔티', price: 15000 },
    { name: '긴팔티', price: 20000 },
    { name: '핸드폰케이스', price: 15000 },
    { name: '후드티', price: 30000 },
    { name: '바지', price: 25000 }
]
```

## 1. Map
### 1.1 기본적인 사용
- 이터러블 내의 값들을 따로 모으는 경우 많이 사용하게 된다. (1:1 매핑)
- array, set 등에는 기본적으로 달려 있다.
- 아래와 같은 일반적인 방법(명령형 방식)을 사용하면 함수 외부에 영향을 끼치게 된다.
```js
// 명령형 버전
const names = [];
for (const product of products) {
    names.push(product.name);
}

// 상품의 이름만 모아서 출력
console.log(names);
```
- FP에서는 이를 방지하기 위해 함수 간 파라미터와 리턴값으로 통신하길 권고한다.
```js
const map = (f, iter) => {
    const result = [];
    for (const a of iter) {
        result.push(f(a));
    }
    return result;
}
```
- map을 활용하면 유연하게 name만 골라오는 동작, price만 골라오는 동작을 사용할 수 있다.(mapExample.js)
```js
console.log("이름만 출력------------");
console.log(rx.map(product => product.name, sampleData.data));

console.log("가격만 출력------------");
console.log(rx.map(product => product.price, sampleData.data));
```

### 1.2 map의 다형성
- 이터러블/이터레이터 프로토콜을 준수하기만 하면 뭐든 다 적용 가능하다.
- filter({적용할 함수}, {처리할 이터러블}) <- 이렇게 사용한다.
```js
function* gen() {
    yield 2;
    yield 3;
    yield 4;
}

console.log(rx.map(a => a * a, gen()));
// [ 4, 9, 16 ]
```

- map 자료형도 이터러블이기 때문에 당연히 적용 가능하다.
```js
const sampleMap = new Map();
sampleMap.set('a', 10);
sampleMap.set('b', 20);

console.log(rx.map(([k, v]) => [k, v * 2], sampleMap));
// ['a', 20], ['b', 40]
```

## 2. Filter
### 2.1 기본적인 사용 (filterExample.js)
- 특정 조건의 값들만 골라내는 함수
- 명령형과 다르게 조건 함수만 바꿔주면 조건에 맞는 이터러블을 얻을 수 있다.
- filter({filter 조건 함수}, {처리할 이터러블}) <- 이렇게 사용한다.
```js
const filter = (f, iter) => {
    const result = [];
    for (const a of iter) {
        if (f(a)) result.push(a);
    }
    return result;
}
```
- map은 인자로 전달된 모든 이터러블 요소에 전달된 함수를 적용시킨다.
- filter는 인자로 전달된 이터러블 중 전달된 함수에 부합하는 요소들만 골라낸다.

## 3. Reduce
### 3.1 기본적인 사용 (reduceExample.js)
- 제시받은 이터러블의 요소들을 하나의 값으로 결합시킨다.
- reduce({reduce에 사용할 함수}, {시작값}, {처리할 이터러블}) <- 이렇게 사용한다.
- 보조 함수를 재귀적으로 실행하면서 처리된다.
```js
const add = (a, b) => a + b;
reduce(add, 0, [1, 2, 3, 4, 5]);

// 내부적으로 이렇게 동작한다.
add(add(add(add(add(0, 1), 2), 3), 4), 5);
```

- 숫자의 총합 같은 거 구할 때 많이 쓴다.
```js
console.log(rx.reduce(add, 0, nums));
// 보통 자바스크립트에서는 시작값을 생략해도 동작하도록 유연하게 구현한다.
console.log(rx.reduce(add, nums));
```

### 3.2 reduce의 다형성
- map, filter와 마찬가지로 이터러블/이터레이터 프로토콜을 준수하면 어떤 것이든 처리 가능하다.
```js
console.log(
    reduce(
        (totalPrice, product) => totalPrice + product.price,
        0,
        products
    )
)
```
- 이 예시에서 시작값을 안주는 경우는 현재 제대로 동작하지 않는다.
- 이 경우 시작하는 totalPrice가 객체로 넘어오기 때문이다.
```js
console.log(
    reduce(
        (totalPrice, product) => totalPrice + product.price,
        products
    )
)
// [object Object]20000150003000025000
```