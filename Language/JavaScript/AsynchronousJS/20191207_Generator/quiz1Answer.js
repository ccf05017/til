const util = require("util");
const fs = require("fs");
const readFile = util.promisify(fs.readFile);

function* fileLoader(files) {
    for (let file of files) {
        yield readFile(file, "utf8");
    }
}

(async () => {
  for await (let contents of fileLoader([
    "./files/demofile.txt",
    "./files/demofile.other.txt"
  ])) {
    console.log(contents);
  }
})();