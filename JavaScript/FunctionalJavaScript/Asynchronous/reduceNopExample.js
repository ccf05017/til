const rx = require("../rx/index.js");

const add = (a, b) => a + b;

rx.go([1, 2, 3, 4],
    rx.L.map(a => Promise.resolve(a * a)),
    rx.L.filter(a => a % 2),
    rx.reduce(add),
    console.log);
