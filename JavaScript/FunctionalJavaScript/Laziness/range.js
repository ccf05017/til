const rx = require("../rx/index.js")

const add = (a, b) => a + b;

// 그냥 range
const range = size => {
    let i = -1;
    let res = [];
    while(++i < size) {
        res.push(i);
    }
    return res;
};

// 게으른 range
const L = {};
L.range = function* (size) {
    let i = -1;
    while(++i < size) {
        yield i;
    }
};

console.log("range 수 합계 구하기(일반 range)");
console.log(`원본: ${range(4)}`);
console.log(`합계: ${rx.reduce(add, range(4))}`);

console.log("range 수 합계 구하기(게으른 range)");
console.log(`원본: ${L.range(4)}`);
console.log(`합계: ${rx.reduce(add, range(4))}`);
