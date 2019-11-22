const fs = require("fs");
const util = require("util");

// node가 기본적으로 제공하는 기가 막힌 기능을 이용할 수도 있음
const readFile = util.promisify(fs.readFile);

// 생으로 Promise를 구현하는 방법
// function readFile(filename, encoding) {
//   return new Promise((resolve, reject) => {

//     fs.readFile(filename, encoding, (err, data) => {

//       if (err) return reject(err); // 이걸 return으로 해야 하는 많은 이유에 대해서 설명했으나 영어라 잘 모르겠..
//       else return resolve(data);
//     });
//   });
// }

// success case
readFile("./files/demofile.txt", "utf-8").then(
  data => console.log(data),
  err => console.log(err)
);

// fail case
readFile("./files/noExistFile.txt", "utf-8").then(
  data => console.log(data),
  err => console.log(err)
);