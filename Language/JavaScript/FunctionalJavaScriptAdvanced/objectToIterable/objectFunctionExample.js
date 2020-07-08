const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");

const input = [['a', 1], ['b', 2], ['c', 3]];

// 이 자체로도 나쁘지 않은 코드
const object = entries => _.go(
    entries,
    L.map(([k, v]) => ({ [k]: v })),
    _.reduce(Object.assign)
);

console.log(object(input));

const mapExample = new Map();
mapExample.set('a', 10);
mapExample.set('b', 20);
mapExample.set('c', 30);

console.log(object(mapExample));
