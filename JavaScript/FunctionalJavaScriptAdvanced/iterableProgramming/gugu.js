const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");

const join = sep => _.reduce((a, b) => `${a}${sep}${b}`);

_.go(
    L.range(2, 10),
    L.map(a => _.go(
        L.range(1, 10),
        L.map(b => `${a} * ${b} = ${a * b}`),
        join('\n')
    )),
    join('\n\n'),
    console.log
);
