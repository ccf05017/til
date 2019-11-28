const fs = require("fs");
const util = require("util");

const readFile = util.promisify(fs.readFile);
const fileNames = ["./files/demofile.txt", "./files/demofile.other.txt"];
const invalidFileNames = ["./files/notExist.txt", "./files/notExist.other.txt"];

// readFile Prmoise가 반환되어 promises에 하나씩 저장됨.
const promises = fileNames.map(fileName => readFile(fileName, "utf8"));

const invalidPromises = invalidFileNames.map(fileName => readFile(fileName, "utf8"));

// then의 결과값을 모아서 array로 저장된다
// 정상적인 경우
Promise.all(promises)
    .then(value => {
        console.log("정상적인 경우");
        console.log(value);
    })

// 비정상적인 경우 - Reject Handler 사용
Promise.all(invalidPromises)
    .then(value => {
        console.log(value);
    },
    err => {
        console.log("비정상적인 경우 - Reject Handler 사용")
        console.log(err); // 첫 에러에서 멈춘다.
    }    
)

// 비정상적인 경우 - Catch 사용
Promise.all(invalidPromises)
    .then(value => {
        console.log(value);
    })
    .catch(err => {
        console.log("비정상적인 경우 - catch 사용")
        console.log(err); // 첫 에러에서 멈춘다.
    })
