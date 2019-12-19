# 제너레이터

## 1. 정의 및 사용법
- 이터러블을 생성하는 이터레이터 함수
- well-formed 이터레이터를 리턴하는 함수
```js
function* gen() {
    yield 1;
    yield 2;
    yield 3;
    
    return 100;     // 마지막 순회 때 반환되는 값
}

const iterator = gen();

console.log(iterator[Symbol.iterator]());
console.log(iterator[Symbol.iterator]() == iterator);

console.log(iterator.next());
console.log(iterator.next());
console.log(iterator.next());
console.log(iterator.next());

for (const a of gen()) console.log(a);  // 순회에서는 return 값 반환은 안함.
```
- 문장을 순회 가능한 값으로 만들 수 있다.
- 어떤 값이던 순회가 가능하게 해준다. (매우 높은 다형성)

## 2. 예제
- 이터러블/이터레이터 프로토콜을 지원하면 호환된다는 성격을 이용해 여러개의 조합을 만들 수 있다.
- 명령형과 다르게 성능 문제 없이 무한한 수를 생성하는 함수를 만드는 것도 가능하다.
```js
function* infinity(i = 0) {
    while (true) yield i++;
}
```
- 로직이 포함된 무한한 수 생성도 가능하다.
```js
// 영원히 짝수만 반환한다.
function* odds(limit) {
    for (const a of infinity(1)) {
        if (a % 2) yield a;
        if (a == limit) return;
    }
}
```
- 이터러블/이터레이터 프로토콜을 준수한다면 어떤 것이든 순회할 수 있다.
```js
// 위와 같은 동작을 하는 코드

// 제한 가능한 무한수열 제너레이터(이터러블/이터레이터 프로토콜 준수)
function* limit(l, iter) {
    for (const a of iter) {
        yield a;
        if (a == limit) return;
    }
}

// odds에 사용하는 이터레이터를 변경한다.
function* odds(l) {
    for (const a of limit(l, infinity(1))) {
        if (a % 2) yield a;
    }
}
```
- 이렇게 만들어진 제너레이터는 당연히 순회하면서 사용도 가능하다.
```js
// 100 이하의 짝수만 반환한다.
for (const a of odds(100)) console.log(a);
```