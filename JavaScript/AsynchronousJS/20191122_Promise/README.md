# Promise란? (basicUsage.js)
- ES6부터 추가 된 콜백의 대체 API
- 기본적으로 콜백과 같은 기능을 수행하지만, 좀 더 이쁘고 에러 다루기가 쉽다.
- Promise에 전달 되는 콜백 안에 비동기 함수를 넣고, 이게 처리 완료될 때 resolve()를 콜한다.

```js
const examplePromise = new Promise((resolve, reject) => {
    // 성공할 경우 resolve() 콜
    // 실패할 경우 reject()콜
});
```

# 사용법(basicUsage.js)
- 보통 함수에서 이 Promise 자체를 리턴하고, 이걸 사용하는 쪽에서 갖다 쓴다.
- reject, resolve에 인자로 값을 전달해주는 것도 가능하다.

```js
function doAsyncTask() {

    const promise = new Promise((resovle, reject) => {
        asyncFunction(() => {
            // 비동기 작업 진행
            if(err) {
                // 작업이 실패하면?
                reject();
            } else {
                // 작업이 끝나면?
                resolve();
            }
        });
    })

    return promise
}
```

- 사용하는 쪽에서는 함수를 콜한 뒤 then() 함수를 이어서 성공 핸들러, 실패 핸들러 두 개를 인자로 전달해줄 수 있다.
```js
doAsyncTask().then(
    () => { // success handler },
    () => { // failure handler }
)
```

# Quiz 1.
## 문제
- 콜백으로 처리하던 파일 읽기를 프로미스 버전으로 전환해보자

```js
const fs = require("fs");

function readFile(filename, encoding) {
  fs.readFile(filename, encoding, (err, data) => {
    //TODO
  });
}
readFile("./files/demofile.txt", "utf-8")
    .then(...)
});
```

## 답안
- 그냥 전체를 Promise로 감싸서 잘 처리하면 된다.
- Node에서 기본적으로 제공하는 util 모듈의 promisify를 사용할 수도 있다.

### 의문점
- 근데 이렇게 계속 짜면 에러 처리는 쉬운 거 같은데, 콜백보다 진짜 편하긴 한건가..?

# 즉시 실행
- 너무 뻔하게 결과가 보이는 경우에는 즉시 프로미스가 resolve(), reject() 할 수 있다.

```js
let promise = Promise.resolve("done");
promise.then(val => console.log(val)); // done
```

# 콜백과의 차이점(promiseIsAsync.js)
- Promise는 언제나 비동기로 처리가 된다.
- 콜백은 내부 함수 실행되는 동안 블록 걸림
- 아래는 Callback예제의 첫 퀴즈와 다른 상황을 보여줌.

```js
function doAsyncTask() {
    return Promise.resolve();
}

doAsyncTask().then(_ => console.log(message));

const message = "This is message";
```

# 체이닝(chaining.js)
- then을 이어서 사용할 수 있다.
- 단, then으로 넘겨줄 때 반드시 *return*이 있어야 한다.
- 포킹(forking.js)와 전혀 다른 개념이다.
- 포킹은 그냥 새로운 then을 실행할 뿐이다. (말로 설명하기 어려움. 코드 참고할 것)

# Quiz2.
## 문제
- 체이닝을 통해 아래 코드에서 파일을 불러온 뒤 압축해서 결과를 출력해보자

```js
const fs = require("fs");
const zlib = require("zlib");

function zlibPromise(data) {
  zlib.gzip(data, (error, result) => {
    //TODO
  });
}

function readFile(filename, encoding) {
  return new Promise((resolve, reject) => {
    fs.readFile(filename, encoding, (err, data) => {
      if (err) reject(err);
      resolve(data);
    });
  });
}

readFile("./files/demofile.txt", "utf-8")
    .then(...) // --> Load it then zip it and then print it to screen
});
```

## 해답
- 프로미스 뒤에 then을 걸고 다시 프로미스를 부르고 then을 걸고 또 걸고...
- 성공, 에러 핸들러 하나씩 달아주고..
- 어째 콜백 지옥과 비슷한 형상이 보이기 시작함

# Promise return을 통해 콜백 헬 해결
```js
Promise.resolve("done")
  .then(val => {
    console.log(val); // done

    return new Promise(resovle => {
      setTimeout(() => resolve("done2"), 1000);
    });
  }).then(val => console.log(val)); // done2
```

#  Quiz3.
## 문제
- Quiz2를 Promise return 해서 체이닝 하는 방식으로 변경해볼 것

## 해답(quiz3Answer.js)
- return을 다시 Promise로 보내서 계속해서 then()을 이어나갈 수 있도록 변경
- quiz2 스타일은 거의 안쓰는 게 좋을듯

# Error Handling(errorHandling.js)
- Promise 에러는 핸들러를 찾을 때까지 계속 체이닝 된다.

```js
Promise.reject("fail")
  .then(val => console.log(val)) // 에러 핸들러가 없기 때문에 그냥 통과
  .then(val => console.log(val), err => console.log(err)); // 내려온 에러가 여기서 처리됨.
```

- Promise에서 throw가 발생하면 진행중이던 걸 멈추고 무조건 에러 핸들러를 찾는다.
- throw가 resolve에서 발생하건 reject에서 발생하건 에러 발생으로 처리된다.
- Promise 체인의 마지막에 catch를 then의 reject 핸들러와 같은 용도로 사용 가능하다.
- catch는 throw 외의 Promise의 reject에도 반응한다.

# Quiz4.
## 문제
- Quiz3의 에러를 catch를 통해 처리해볼 것

## 해답(quiz4Answer.js)
- 그냥 제일 마지막에 catch 달아주면 된다.

# Finally(finally.js)
- 정상, 에러에 상관없이 가장 마지막에 반드시 처리하는 부분
```js
Promise.resolev("done")
  .then(val => {
    throw new Error("fail");
  })
  .then(val => console.log(val)) // 에러 발생해서 무시됨.
  .catch(val => console.log(err)) // 위에서 던진 에러를 출력
  .finally(_ => console.log("this part must excute") // 이 부분은 무조건 실행됨.
);
```

# Promise.all(promiseAll.js)
- Java에서 stream() 처리 하듯이 Promise를 처리할 수 있음.
- 좀 더 보기 직관적임
- 반복되는 각각의 promise에 핸들러를 달아줄 수 있음