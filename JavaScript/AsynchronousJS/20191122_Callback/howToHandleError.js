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
    // next(err);
  } else {

    // err 때문에 못옴
  }
});