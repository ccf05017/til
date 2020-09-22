const util = require("util");
const fs = require("fs");
const readFile = util.promisify(fs.readFile);

const fileIterator = files => ({
    [Symbol.asyncIterator]: () => ({
        x: 0,
        next() {
            // TODO
            if (this.x >= files.length) {
                return Promise.resolve({
                    done: true  // 별다른 동작 없이 이터레이터를 끝내기만 한다.
                })
            }

            let file = files[this.x++]

            return readFile(file, "utf8").then(data => ({
                done: false,
                value: data
            }));
        }
    })
});
  
(async () => {
    for await (let x of fileIterator([
        "./files/demofile.txt",
        "./files/demofile.other.txt"
    ])) {
        console.log(x);
    }
})();