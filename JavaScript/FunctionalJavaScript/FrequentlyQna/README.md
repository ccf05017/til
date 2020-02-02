# 자주 물어볼만한 질문들에 대해
- 유인동님이 자주 받으신 질문들에 대한 답변 모음집

## 1. 왜 FxJS의 map을 굳이 따로 쓰나요?
- map 외에 filter, reduce 등등도 포함되는 질문
- array 프로토타입에 있는 해당 함수들은 Promise 값을 계산할 수 없다.
- 기존의 프로토타입에 있는 함수들은 Promise를 제어하지 않는다.
- if, for 같은 명령형에 대해서만 동작한다.
```js
async function dealyI(a) {
    return new Promise(resolve => setTimeout(() => resolve(a), 100));
}

function f2() {
    const list = [1, 2, 3, 4, 5];
    const res = list.map(a => dealyI(a * a));
    console.log(res);
}

f2();   // Promise 뭉치만 선물로 줄 것
```

- async/await 조합으로 될 거 같은데요? -> 네 안됩니다.
```js
async function f3() {
    const list = [1, 2, 3, 4, 5];
    const res = await list.map(async a => await dealyI(a * a));
    console.log(res);
}

f3();
```
