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
    .then(fileReadData => {
        gzipPromise(fileReadData).then(
            fileCompressData => console.log(fileCompressData),
            err => console.log(`Compress Failed: ${err}`)
        );
    }, err => {
        console.log(`File Read Failed: ${err}`)
    });