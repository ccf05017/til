# Quiz 1.
## 문제
- 메시지가 출력되도록 변경해보기
- 현재는 메시지가 정의 되어 있지 않다고 나올 것
- let message = "Callback Called"; <- 이 구문 위치는 바꿀 수 없다. 

```javascript
function doAsyncTask(cb) {
  cb();
}
doAsyncTask(_ => console.log(message));

let message = "Callback Called";
```

## 해답(quiz1Answer.js)
- 콜백은 기본적으로 블로킹 동작이다(비동기 처리 안함)
- 함수가 완료될 때까지 멍하고 기다리기 때문에 아래 있는 message 변수를 못 갖고 와서 에러가 난다.
- 그래서 이벤트 틱에 등록해주면 아래 message 변수까지 다 읽고 정상적으로 실행된다.(반추측)

# 에러 처리 방법(howToHandleError.js)
1. throw를 통해 던지고 그냥 끝내는 방법
2. 콘솔에 로그를 남기고 return으로 함수를 종료시키는 방법
3. 또 다른 콜백을 통해 다음 함수 체인으로 전달하는 방법

# Quiz 2.
## 문제
- 아래 코드는 에러를 그냥 숨긴다.
- 에러 숨기지 않고 다음 콜백이 출력할 수 있도록 변경해보자.

```javascript
const fs = require("fs");

function readFileThenDo(next) {
  fs.readFile("./blah.nofile", (err, data) => {
    next(data);
  });
}

readFileThenDo(data => {
  console.log(data);
});
```

## 해답(quiz2Answer.js)
- 그냥 다음 콜백이 받아서 처리하도록 하면 된다.
- 에러 케이스를 좀 더 명확하게 다루고 싶다면, if구문 통해서 구분해서 보내주면 됨

# Quiz 3.
## 문제
- 아래 코드에서 trace 터지지 않고 잡아서 처리할 수 있도록 바꿀 것

```javascript
const fs = require("fs");

function readFileThenDo(next) {
  fs.readFile("./blah.nofile", (err, data) => {
    if (err) throw err;
    next(data);
  });
}
// Hint use try..catch
readFileThenDo(data => {
  console.log(data);
});
```

## 해답(quiz3Answer.js)
- try-catch로 잡으려 해도 기본적으로 자바스크립트 함수는 비동기이기 때문에 잡을 수 없다.
- readFileThenDo() 함수 전체를 try로 감싸도, 비동기 함수이기 때문에 throw가 나오기 전에 그냥 스쳐 지나간다.
- 결론은 비동기 함수에 쓸데 없이 try-catch 걸지 말고, blocking 함수에만 걸어라