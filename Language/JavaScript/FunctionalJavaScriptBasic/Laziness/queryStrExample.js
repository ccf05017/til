const rx = require("../rx/index.js")

const L = {};

L.entries = function* (obj) {
    for (const k in obj) yield [k, obj[k]];
}

const join = rx.curry((sep = ',', iter) => 
    rx.reduce((a, b) => `${a}${sep}${b}`, iter));

const queryStr = rx.pipe(
    L.entries,
    rx.L.map(([k, v]) => `${k}=${v}`),
    join('&')
);

console.log(queryStr({ limit: 10, offset: 10, type: 'notice' }));
