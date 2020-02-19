const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");

const m = new Map();
m.set('a', 1);
m.set('b', 2);
m.set('c', 3);

// 이터러블로 다루기
_.go(
    m,
    _.filter(([k, v]) => v % 2),
    _.takeAll,
    console.log
);

// 다시 Map으로 바꾸기
_.go(
    m,
    _.filter(([k, v]) => v % 2),
    entries => new Map(entries),
    console.log
);

const s = new Set();
s.add(10);
s.add(20);
s.add(30);

const add = (a, b) => a + b;

console.log(_.reduce(add, s));
