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