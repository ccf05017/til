# 1. Async/Await란?
- 2017년쯤 추가 된 기능
- await를 통해 promise의 resolve, reject를 처리 가능하다.
- aysnc로 지정된 함수에서만 await 사용 가능 

# 2. 특징
## 1) Promise와의 차이점
- Promise는 기다리는 거 없이 그냥 바로 직진해서 비동기 처리해버린다.

```js
cosnt doAsyncTask = () => Promise.resolve("done");
doAsyncTask().then(val => console.log(val));
console.log("here");

// 'here -> done'순으로 출력
```

- await를 통해 비동기 처리가 끝날 때까지 기다리도록 할 수 있다.
- 비동기 함수를 블로킹 처리 가능.

```js
const doAsyncTask = () => Promise.resolve("done");

async function test() {
    const value = await doAsyncTask(); // then 사용 안해도 resolve가 실행된다.
    console.log(value);
    console.log("here");
}

test();

// 'done -> here'순으로 출력
```

## 2) Async 함수는 Promise로 처리 가능하다.

```js
const doAsyncTask = () => Promise.resolve("1");

const asyncFunction = async () => {
    const value = doAsyncTask();
    console.log(value);
    console.log("2");

    return 3;
}

asyncFunction().then(val => console.log(val));

// '1 -> 2 -> 3'순으로 출력
```

# 3. Error 처리
- 기존 블로킹 함수에서 사용하듯 try/catch 사용이 가능하다. (Promise에서는 기존처럼 사용 어려움)

```js
const doAsyncTask = () => Promise.reject("error");

const asyncFunction = async () => {
    try {
        const value = await doAsyncTask();
        console.log(value);
    } catch(e) {
        console.log(e);
        return;
    }
}

asyncFunction();

// 'error' 출력
```