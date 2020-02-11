const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");

const obj = {
    a: 1,
    b: 2,
    c: 3,
    d: 4
};

_.go(
    obj,
    Object.values,
    _.reduce((a, b) => a + b),
    console.log
);

L.values = function *(obj) {
    for (const k in obj) {
        yield obj[k];
    }
};

_.go(
    obj,
    L.values,
    L.map(a => a + 10),
    L.take(2),
    _.reduce((a, b) => a + b),
    console.log
);
