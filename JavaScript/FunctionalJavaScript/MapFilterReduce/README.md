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
```