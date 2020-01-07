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

## range, map, filter, take, reduce의 중첩 사용
### 기본 함수(평가지연 없음)
- 결과는 차이가 없다.
- 동작 방식에 차이가 있을 뿐
```js
console.log("지연 평가 없는 버전");
rx.go(
    rx.range(10),
    rx.map(n => n + 10),
    rx.filter(n => n % 2),
    rx.take(2),
    console.log
);
```
- 다음 함수로 넘어가기 전에 이미 평가가 모두 완료된 상태로 넘어간다.

### 게으른 함수(평가지연 있음)
- 필요할 때만 연산을 수행하며 진행한다.
- 실행 순서가 일반적인 방식과 역순으로 진행된다.
- 기존 방식이 가로를 완료하고 세로로 내려가는 책읽기 순서라면 게으른 함수는 세로를 먼저 진행한다.
- 게으른 쪽이 성능이 좋다.
- 다량의 연산을 진행할수록 성능 차이가 어마어마하게 커진다.
```js
console.log("지연 평가 버전");
rx.go(
    rx.L.range(10),             // 4
    rx.L.map(n => n + 10),      // 3
    rx.L.filter(n => n % 2),    // 2
    rx.take(2),                 // 1
    console.log
);

console.log("지연 평가 버전(무한수열)");
rx.go(
    rx.L.range(Infinity),
    rx.L.map(n => n + 10),
    rx.L.filter(n => n % 2),
    rx.take(2),
    console.log
);
```

## map, filter 결합법칙
- 평가 순서를 일반적인 함수와 다르게 가져가도 똑같은 결과를 반환한다.
- 사용하는 데이터가 무엇이건 사용하는 보조 함수가 순수 함수라면 아래의 결합은 늘 결과가 같다.
```js
[[mapping, mapping], [filtering, filtering], [mapping, mapping]]
=
[[mapping, mapping, filtering], [filtering, mapping, mapping]]
```

## 지연성에서 ES6의 장점?
- 흑마법이 아니라 공식적인 언어 기능을 통해 지연 평가를 구현할 수 있다.
- 안전하다.
- 조합성이 높다.
