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
* 근데 이렇게 계속 짜면 에러 처리는 쉬운 거 같은데, 콜백보다 진짜 편하긴 한건가..?