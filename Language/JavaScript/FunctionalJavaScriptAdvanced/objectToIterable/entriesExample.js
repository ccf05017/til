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
    L.entries,
    L.filter(([_, v]) => v % 2),
    L.map(([k, v]) => ({ [k]: v })),
    _.reduce(Object.assign),
    console.log
);
