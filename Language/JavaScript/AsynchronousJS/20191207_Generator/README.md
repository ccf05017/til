# 1. Generator란?
- 함수 중간에 yield 키워드를 통해 멈출 수 있는 함수
- function* 키워드로 생성할 수 있다.
- 일반 함수가 켠 김에 왕까지 해야된다면, 제너레이터는 중간 저장 지점이 있는 함수
- 이터레이터를 반환하기 때문에 next()를 통해 동작을 제어할 수 있다.

```js
function* generatorDemo() {
    console.log("1");
    yield;
    console.log("2");
}
console.log("start")
const it = generatorDemo();
console.log("before iterate");
console.log(it.next());
console.log(it.next());
console.log(it.next());
console.log("after iterate");

// 아래 순서로 출력된다.
// start -> before iterate -> 1 -> {value: undefined, done: false} -> 2 ->
// {value: undefined, done: true} -> {value: undefined, done: true} -> "after iterate"
```

# 2. 사용 예시
## 2.1 yield를 통한 데이터 출력 제어
- yield를 통해 필요할 때마다 데이터를 하나씩 꺼낼 수 있다.

```js
function* range() {
    for (let i = 0; i < 4; i ++) {
        yield i;
    }
    yield "moo";
}

const it = range();
console.log(it.next()); // { value: 0, done: false }
console.log(it.next()); // { value: 1, done: false }
console.log(it.next()); // { value: 2, done: false }
console.log(it.next()); // { value: 3, done: false }
console.log(it.next()); // { value: 'moo', done: false }
console.log(it.next()); // { value: undefined, done: true }
```

## 2.2 이터레이터처럼 사용하기
- 아예 이터레이터처럼 사용할 수 있다.
- for ... of 구분 적용이 가능하다는 의미
```js
function* range() {
    for(let i = 0; i < 10; i++) {
        yield i;
    }
}

for (let x of range()) {
    console.log(x);
}

// 1, 2, 3, 4, 5, 6, 7, 8, 9
```

## 2.3 yield는 양방향으로 사용할 수 있다.(bothYield.js)
- 제어권을 주고 받으면서 외부에서 사용할 수 있다.
- 사실 아직 잘 모르겠음 이게 왜 되지..?

```js
function* hello() {
    console.log(yield);
    console.log("World");   // 3. World 출력하면서 종료됨
}

const it = hello();
it.next();                  // 1. yield 이전까지만 실행됨
it.next("Hello");           // 2. yield에 Hello를 넘기면서 실행됨

// Hello -> World 로 출력된다.
```

# 3. Custom Async Generator(asyncGenerator.js)
- 제너레이터 자체를 Custom Iterator로 감쌀 수 있다.
```js
function* range() {
    for (let i = 0; i < 10; i++) {
        yield Promise.resolve(i);
    }
}

// Promise가 출력됨
(async () => {
    for (let x of range()) {
        console.log(x);
    }
})();

// await를 통해 실제로 값을 받아서 출력
(async () => {
    for await (let x of range()) {
        console.log(x);
    }
})();
```

# 4. Quiz1
## 문제
- 아래 코드를 완성해보자

```js
const util = require("util");
const fs = require("fs");
const readFile = util.promisify(fs.readFile);

function* fileLoader(files) {...}

(async () => {
  for await (let contents of fileLoader([
    "./files/demofile.txt",
    "./files/demofile.other.txt"
  ])) {
    console.log(contents);
  }
})();
```

## 풀이(quiz1Answer.js)