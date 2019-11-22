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

## 해답
- 콜백은 기본적으로 블로킹 동작이다(비동기 처리 안함)
- 함수가 완료될 때까지 멍하고 기다리기 때문에 아래 있는 message 변수를 못 갖고 와서 에러가 난다.
- 그래서 이벤트 틱에 등록해주면 아래 message 변수까지 다 읽고 정상적으로 실행된다.(반추측)

# 에러 처리 방법
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