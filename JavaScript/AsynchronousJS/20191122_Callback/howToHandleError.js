const fs = require("fs");

// 정상 처리
fs.readFile("./files/demofile.txt", { encoding: "utf8" }, (err, data) => {
  if (err) {

    // 무시
  } else {

    console.log("** Works Well Case **");
    console.log(data);
    console.log("\n");
  }
});

// 에러 다루는 방법
fs.readFile("./files/notFileExist.txt", { encoding: "utf8" }, (err, data) => {
  if (err) {

    // ** 1. 그냥 에러를 던진다 (일반적인 try catch 처럼) **
    // throw err; 

    // ** 2. 콘솔로 에러를 찍고 return으로 함수를 종료시킨다. **
    console.log(err);
    console.log("\n");
    return;

    // ** 3. 다음 함수 체인으로 던져준다. **
    // 여기서 next()는 내가 맹글어서 콜백 함수로 전달 준 게 있어야 사용 가능!
    // 기본적으로 제공되는 애가 아님!
    // next(err);
    // thisFunctionIsForError(err); <- 이런 것도 가능하다는 얘기
  } else {

    // err 때문에 못옴
  }
});