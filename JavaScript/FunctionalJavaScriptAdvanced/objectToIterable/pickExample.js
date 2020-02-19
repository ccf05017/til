const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");

const obj = { a: 1, b: 2, c: 3, d: 4, e: 5 };

const pick = (keys, obj) => _.go(
    keys,
    _.map(k => [[k], obj[k]]),
    _.object
);

console.log(pick(['b', 'c'], obj));

const pickOnlyExist = (keys, obj) => _.go(
    keys,
    _.map(k => [[k], obj[k]]),
    _.reject(([k, v]) => v === undefined),
    _.object
);

console.log(pickOnlyExist(['b', 'c', 'z'], obj));
