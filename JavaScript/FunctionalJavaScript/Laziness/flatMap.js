const rx = require("../rx/index.js")

arr = [[1,2],[3,4],[5,6,7],[10]];

console.log([[1,2],[3,4],[5,6,7],[10]].flatMap(a => a));

const L = {};

L.flatMap = rx.curry(rx.pipe(rx.L.map, rx.L.flatten));

const it = L.flatMap(rx.map(a => a * a), arr);
console.log([...it]);

const it2 = rx.L.flatMap(rx.map(a => a * a), arr);
console.log(...it2);
