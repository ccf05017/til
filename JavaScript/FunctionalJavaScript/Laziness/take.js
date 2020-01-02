const rx = require("../rx/index.js")

const range = size => {
    let i = -1;
    let res = [];
    while(++i < size) {
        res.push(i);
    }
    return res;
};

const L = {};
L.range = function* (size) {
    let i = -1;
    while(++i < size) {
        yield i;
    }
};

const take = rx.curry((limit, iter) => {
    let res = [];
    for (const a of iter) {
        res.push(a);
        if (res.length == limit) return res;
    }
    return res;
});

console.log(take(5, range(100)));
console.log(take(5, L.range(100)));
console.log(take(5, L.range(Infinity)));

console.log("일반 range");
console.time('');
rx.go(
    range(10000),
    take(5),
    console.log
);
console.timeEnd('');

console.log("게으른 range");
console.time('');
rx.go(
    L.range(10000),
    take(5),
    console.log
);
console.timeEnd('');
