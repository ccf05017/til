const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");

const f = x => x + 10;
const g = x => x - 5;
const fg = x => f(g(x));

// 정상적인 경우
_.go(
    [10],
    L.map(fg),
    _.each(console.log)
);

// 비정상적인 경우 -> 아예 연산이 실행되지 않는다.
_.go(
    [],
    L.map(fg),
    _.each(console.log)
);
