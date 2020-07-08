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

# 4. Quiz1
## 문제
- 아래의 코드를 aysnc/await 버전으로 변경해보기
```js
const util = require("util");
const fs = require("fs");
const readFile = util.promisify(fs.readFile);

const files = ["./files/demofile.txt", "./files/demofile.other.txt"];

let promises = files.map(name => readFile(name, { encoding: "utf8" }));
Promise.all(promises).then(values => {
  // <-- Uses .all
  console.log(values);
});
```

## 풀이(quiz1Answer.js)
- async/await만을 사용해서 블로킹 방식으로 구현할 수 있다.
- Promise.all()과 결합하여 논블로킹 방식의 parallel을 구현할 수 있다.
- 순서가 정해진 프로세스가 아니라면, Promise.all()과 결합 사용을 통해 더 효율적인 코드를 구현할 수 있다.

# 5. await는 꼭 필요할 때만 사용하자. (noAwait.js)
- async는 흑마법이 아니다. 당연하게도.
- async를 사용하기만 한다고 함수가 갑자기 비동기로 바뀌거나 그러지 않는다.

```js
const printFunc1 = async () => {
    console.log("1");
}

const printFunc2 = async () => {
    console.log("2");
}

const main = async () => {
    printFunc1();
    printFunc2();
    console.log("finished");
}

main(); // 1 -> 2 -> finished 순으로 출력
```

- 내부 구현 자체가 비동기로 구현되어 있어야 제대로 동작한다.
```js
const printFunc1 = async () => {
    setImmediate(_ => console.log("1"));
}

const printFunc2 = async () => {
    console.log("2");
}

const main = async () => {
    printFunc1();
    printFunc2();
    console.log("finished");
}

main(); // 2 -> finished -> 1 순으로 출력
```

# 6. Async Iterator
## 개요
- Node 10부터 추가된 기능
- for - await - of 형태로 prmoise 뭉치를 사용할 수 있다.
- 블로킹 방식으로 처리된다.

```js
(async () => {
    const util = require("util");
    const fs = require("fs");
    const readFile = util.promisify(fs.readFile);

    const files = ["./files/demofile.txt", "./files/demofile.other.txt"];
    const promises = files.map(name => readFile(name, "utf8"));
    for await (const promise of promises) {
        console.log(file);
    }
})();
```

## 이터레이터?(iteratorExample.js)
- Symbol.iterator 속성을 갖고 있는 객체
- next() 함수를 통해 '{done: false, value: ?}'를 값으로 반환한다.
- 이터레이터를 종료하고 싶을 때는 done: true로 설정해주면 된다.
- 비동기 이터레이터도 생성 가능하다. (Promise를 리턴함)

### arrow function의 특별한 용법
- () => ({})를 통해 대괄호 안의 내용 그 자체를 값으로 전달한다.
- 클로저랑 유사해 보이는데..? (확인 필요)

```js
// Custom Iterator Example
const customIterator = () => ({
    [Symbol.iterator]: () => ({
        x: 0,
        next() {
            if (this.x > 100) {
                return {
                    done: true,
                    value: this.x
                };
            }
            return {
                done: false,
                value: this.x++
            };
        }
    })
});

// Custom Async Iterator Example
const asyncCustomIterator = () => ({
    [Symbol.asyncIterator]: () => ({
        x: 0,

        next() {
            if(this.x > 100) {
                return Promise.resolve({
                    done: true,
                    value: this.x
                });
            }

            let y = this.x++;

            return Promise.resolve({
                done: false,
                value: y
            });
        }
    })
});
```

# 7. Quiz2
## 문제
- 파일 읽기 예제를 custom async iterator 형태로 바꾸기
```js
const fileIterator = files => ({
  [Symbol.asyncIterator]: () => ({
    x: 0,
    next() {
      // TODO
    }
  })
});

(async () => {
  for await (let x of fileIterator([
    "./files/demofile.txt",
    "./files/demofile.other.txt"
  ])) {
    console.log(x);
  }
})();
```

## 풀이(quiz2Answer.js)
- 이해 잘 안감 ㅡㅡ 다시 잘 볼 것

# 8. 강의자(Asim Hussain) 당부의 말
- 비동기 함수는 성능상의 큰 이점을 준다.
- async/await는 블로킹 방식으로 전환해서 개발을 편하게 해주지만, 위의 성능상 이점을 버려야 한다.
- 그러니 사용, 설계에 늘 주의를 기울여 줬으면 좋겠다.(await 남발하지 않았으면 좋겠다.)
- 추천하는 방안을 가능한 작은 함수 단위로 나눠서 개발하는 것.
- 함수에 기능을 마구마구 때려넣고 그걸 await로 기다리면 성능상 손해가 너무 크다.