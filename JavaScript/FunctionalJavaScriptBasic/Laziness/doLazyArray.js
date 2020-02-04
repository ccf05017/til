const rx = require("../rx/index.js");

const arr = [
    [1, 2],
    [3, 4, 5],
    [6, 7, 8],
    [9, 10]
];

const takeAll = rx.take(Infinity);

rx.go(
    arr,
    rx.L.flatten,
    rx.L.filter(a => a % 2),
    rx.L.map(a => a * a),
    takeAll,
    rx.reduce((a, b) => a + b),
    console.log
);
