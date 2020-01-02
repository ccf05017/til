# 게으른 처리(Laziness)
## range 예시(rage.js)
- 숫자 하나를 입력 받아 숫자의 크기만한 배열을 리턴
- 일반 range는 실행하는 순간 평가가 끝난다.
- 일반 range는 필요도 없는데 이미 준비된 상태와 같음.
- 게으른 range는 필요한 순간에만 평가가 수행된다.
- 게으른 range는 이터레이터가 내부를 순회하는 순간 외에는 절대로 수행되지 않는다.
- next() 때리기 전까지는 수행 안된다는 의미
```js
const add = (a, b) => a + b;

// 그냥 range
const range = size => {
    let i = -1;
    let res = [];
    while(++i < size) {
        res.push(i);
    }
    return res;
};

// 게으른 range
const L = {};
L.range = function* (size) {
    let i = -1;
    while(++i < size) {
        yield i;
    }
};

console.log("range 수 합계 구하기(일반 range)");
console.log(`원본: ${range(4)}`);                   // [0,1,2,3]
console.log(`합계: ${rx.reduce(add, range(4))}`);   // 6

console.log("range 수 합계 구하기(게으른 range)");
console.log(`원본: ${L.range(4)}`);                 // [object Generator]
console.log(`합계: ${rx.reduce(add, range(4))}`);   // 6
```

## take(take.js)
- 많은 값을 받아서 잘라주는 함수
- 제시된 한계치만큼만 이터러블을 실행해주는 함수
- 이터러블 프로토콜을 따른다면, 지연 평가도 얼마든지 실행할 수 있다.
- 쓸모 없는 값을 생산하지 않고 효율적으로 생산할 수 있다. => 무한수열 처리 가능
```js
const take = (limit, iter) => {
    let res = [];
    for (const a of iter) {
        res.push(a);
        if (res.length == limit) return res;
    }
    return res;
}

console.log(take(5, range(100)));       // [0,1,2,3,4]
console.log(take(5, L.range(100)));     // [0,1,2,3,4]

console.log(take(5, L.range(Infinity)));    // 지연 처리에서만 가능한 동작
```

- 가벼운 성능 비교
```js
console.log("일반 range");
console.time('');
rx.go(
    range(10000),
    take(5),
    console.log
);
console.timeEnd('');

console.log("게으른 range");
console.time('');
rx.go(
    L.range(10000),
    take(5),
    console.log
);
console.timeEnd('');
```

## 지연 평가
- '제때 계산법', '느긋한 계산법'이라 불리기도 한다.
- 제너레이터 / 이터레이터 프로토콜을 기반으로 구현된다.
- 컬렉션(리스트) 중심의 프로그래밍과 같은 말

## 게으른 map (lazyMap.js)
- 지연성을 갖고 있는 map
- 이터러블 프로토콜 기반의 제너레이터 함수
- 평가를 미룰 수 있기 때문에 평가 순서를 조작할 수 있는 이터레이터를 반환.
```js
const L = {};

const add3 = a => a + 3;

L.map = function* (f, iter) {
    for (const a of iter) yield f(a);
};

var it = L.map(add3, [1, 2, 3]);
console.log(it.next());
```

## 게으른 filter(lazyFilter.js)
- 기존과 거의 유사함
```js
const L = {};

L.filter = function* (f, iter) {
    for (const a of iter) if (f(a)) yield a;
};

var it = L.filter(a => a % 2, [1, 2, 3, 4]);
```