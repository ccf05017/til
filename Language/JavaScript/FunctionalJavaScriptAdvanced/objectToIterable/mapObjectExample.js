const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");

// 함수형 사고 방식
// [['a', 1], ['b', 2], ['c', 3]]
// [['a', 11], ['b', 12], ['c', 13]]
// { a: 11 }, { b: 12 }, { c: 13 }
// { a: 11, b: 12, c:13 }

const object = entries => _.go(
    entries,
    L.map(([k, v]) => ({ [k]: v })),
    _.reduce(Object.assign)
);

const mapObject = (f, obj) => _.go(
    obj,
    L.entries,                              // [['a', 1], ['b', 2], ['c', 3]]
    _.map(([k, v]) => [k, f(v)]),           // [['a', 11], ['b', 12], ['c', 13]]
    object
);

console.log(mapObject(a => a + 10, { a: 1, b: 2, c: 3}));