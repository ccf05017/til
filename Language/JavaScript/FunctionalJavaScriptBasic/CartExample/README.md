# 응용 예제
## 장바구니(cartExample.js)
- 강의 내의 HTML 예제는 제외하고 단순 CLI만 구현.
- 각 단계 별로 추상화 레벨을 높여가며 진행.
- 사용할 예시 데이터는 아래와 같다.
```js
const cartData = [
    { name: '반팔티', price: 15000, quantity: 1 },
    { name: '긴팔티', price: 20000, quantity: 2 },
    { name: '핸드폰케이스', price: 15000, quantity: 3 },
    { name: '후드티', price: 30000, quantity: 4 },
    { name: '바지', price: 25000, quantity: 5 }
]
```

- 상품의 총 수량을 구하는 함수를 다음과 같이 작성 할 수 있다.
```js
const total_quantity = rx.pipe(
    rx.map(p => p.quantity),
    rx.reduce((a, b) => a + b)
);

console.log(total_quantity(cartData));
```

- 비슷한 방식으로 총 가격의 합계를 구할 수도 있다.
```js
const total_price = rx.pipe(
    rx.map(p => p.quantity * p.price),
    rx.reduce((a, b) => a + b)
);

console.log(total_price(cartData));
```

- 자세히 보니 reduce 안의 함수가 겹친다. 기분 나쁘다. 빼내자
```js
const add = (a, b) => a + b

const total_quantity = rx.pipe(
    rx.map(p => p.quantity),
    rx.reduce(add)
);

const total_price = rx.pipe(
    rx.map(p => p.quantity * p.price),
    rx.reduce(add)
);
```

- 좀 더 자세히 보니까 각 pipe 안의 함수가 거의 완전히 같다. 기분 나쁘다. 빼내자
```js
const add = (a, b) => a + b

const sum = (f, iter) => rx.go(
    iter,
    rx.map(f),
    rx.reduce(add)
)

const total_quantity = sum(p => p.quantity, cartData)

const total_price = sum(p => p.quantity * p.price, cartData)
```

- 뭔가.. 뭔가..! 더 줄이고 싶다!! => curry를 써보자
```js
const sum = rx.curry((f, iter) => rx.go(
    iter,
    rx.map(f),
    rx.reduce(add)
));

const total_quantity = products => sum(p => p.quantity)(products);

const total_price = products => sum(p => p.quantity * p.price)(products);
```

- total_quantity, total_price를 보면 여전히 products를 받아서 그대로 넘겨준다. 못생겼다. 빼내자
```js
const total_quantity = sum(p => p.quantity);

const total_price = sum(p => p.quantity * p.price);
```

- 최종적으로 완성된 sum() 함수는 product 도메인에서 해방됐다.(높은 추상화)
```js
const total_age = sum(person => person.age);

console.log(total_age([
    {age: 10},
    {age: 20},
    {age: 30},
]));

// 이렇게 쓸 수도 있다.
console.log(sum(person => person.age, [
    {age: 10},
    {age: 20},
    {age: 30}
]));
```
