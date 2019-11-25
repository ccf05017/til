const fs = require("fs");
const zlib = require("zlib");

function gzipPromise(data) {
  return new Promise((resolve, reject) => {
    zlib.gzip(data, (err, result) => {
      if (err) reject(err);
      resolve(result);
    })
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
    .then(data => {
        return gzipPromise(data);
    },
    err => console.log(`File Read Error: ${err}`))
    .then(data => {
        console.log(data);
    },
    err => console.log(err));