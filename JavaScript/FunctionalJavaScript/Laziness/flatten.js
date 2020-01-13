const rx = require("../rx/index.js")

const takeAll = rx.take(Infinity);

const isIterable = a => a && a[Symbol.iterator];

const L = {};

L.flatten = function* (iter) {
    for (const a of iter) {
        if(isIterable(a)) for (const b of a) yield b;
        else yield a;
    }
}

var it = L.flatten([[1, 2], 3, 4, [5, 6], [7, 8, 9]]);
console.log(...it);
console.log(rx.take(3, L.flatten([[1, 2], 3, 4, [5, 6], [7, 8, 9]])));

const flatten = rx.pipe(L.flatten, takeAll);
console.log(flatten([[1, 2], 3, 4, [5, 6], [7, 8, 9]]));
