# 시간을 이터러블로 다루기
- 시간 타입 (Date)를 다루는 파트가 아니다.
- 코드가 실행되는 시점에 관한 이야기.
- 즉, 시간이 걸리는 일들(I/O bound, 무거운 작업 등)에 대한 이야기
- 작업(=해야되는 일, 연산)을 이터러블의 단위로 보는 방법에 관한 이야기

## 1. range, take의 재해석
- 기존의 관점: 
    - range: 정해진 숫자 만큼을 반환하는 배열
    - take: 주어진 배열을 앞에서부터 정해진 숫자만큼 잘라내기
- 새로운 관점:
    - range: 최대 정해진 수만큼 일어날 수 있는 일. 이터러블
    - take: 최대 정해진 수만큼 일을 수행. 이터러블
- 즉, 기존에는 이미 일이 얼마나 발생할지 가정했다면, 새로운 관점에서는 일어날 가능성만 염두하고 연산을 필요할 때까지만 한다. 게으르게!
```js
_.go(
    L.range(Infinity),
    L.take(3),
    _.each(console.log)
);
```
- 이 약간의 차이가 지닌 의미를 이해하고 자연스러워지도록 연습해야 한다.
- 위와 같은 사고방식을 응용하면 쓸모 없는 자원 낭비를 줄일 수 있다.
```js
_.go(
    L.range(10),
    L.map(_.delay(1000)),
    L.take(3),
    _.each(console.log)
);
```

## 2. takeWhile, takeUntil
- take를 좀 더 동적으로 다루는 함수
- filter와 take를 합한 것처럼 동작한다.
- 명령형 기준으로는 if문이 포함된 반복문으로 생각해도 좋다.

### 2.1 takeWhile
- 주어진 함수 조건에 맞는 것들을 false가 나올때까지 골라낸다.
```js
const example = [1, 2, 3, 4, 5, 6, 7, 8, 0, 0, 0];

_.go(
    example,
    _.takeWhile(a => a),
    _.each(console.log)
);
```
- 이터러블에서 false가 하나라도 나오는 순간 그 뒤는 보지 않는다.
```js
// 아래 이터러블에서 뒤의 10은 출력되지 않는다.
const example2 = [1, 2, 3, 4, 5, 6, 7, 8, 0, 10, 0];

_.go(
    example2,
    _.takeWhile(a => a),
    _.each(console.log)
);
```

### 2.2 takeUntil
- 주어진 함수 조건에 맞는 첫 요소를 만날때까지만 작동한다.
```js
_.go(
    example,
    _.takeUntil(a => !a),
    _.each(console.log)
);
```
