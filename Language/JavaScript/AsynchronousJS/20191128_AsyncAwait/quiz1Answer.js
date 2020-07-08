const util = require("util");
const fs = require("fs");
const readFile = util.promisify(fs.readFile);

const files = ["./files/demofile.txt", "./files/demofile.other.txt"];

// 강의 답안1(blocking)
(async () => {
    for (const file of files) {
        const value = await readFile(file, 'utf8'); // 이 파일을 다 읽어올 때까지 실행을 멈춘다.
        console.log(value)
    }
})();

// 강의 답안2(non-blocking)
(async () => {
    const promises = files.map(file => readFile(file, 'utf8'));
    const values = await Promise.all(promises); // 비동기로 프로미스들을 모두 실행하고 list 형태로 반환한다.
    console.log(values)
})();

// 자체 답안
// files.forEach(async (file) => {
//     content = await readFile(file, 'utf-8');
//     console.log(content);
// });