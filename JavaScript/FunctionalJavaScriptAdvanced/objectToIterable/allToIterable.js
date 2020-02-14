const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");

const g1 = function *(stop) {
    let i = -1;
    while (++i < stop) {
        yield 10;
        if (false) yield 20 + 30;
        yield 30;
    }
}

console.log([...g1(3)]);
