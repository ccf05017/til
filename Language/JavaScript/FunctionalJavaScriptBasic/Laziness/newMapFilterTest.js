const rx = require("../rx/index.js")

console.log(rx.map(a => a + 10, rx.L.range(10)));
console.log(rx.filter(a => a < 5, rx.L.range(10)));
