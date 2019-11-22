const fs = require("fs");

const validFilePath = "./files/demofile.txt";
const invalidFilePath = "./invalidFilePath.txt"

function readFileThenDo(next) {
  fs.readFile(invalidFilePath, "utf8" ,(err, data) => {
    if (err) {
        next(err); // 인자가 한 개일 때는 앞에 꺼만 채워주는 거 같다(추측)
    } else {
        next(null, data);
    }
  });
}

readFileThenDo((err, data) => {
  if (err) {
    console.log(err);    
  } else {
    console.log(data);
  }
});