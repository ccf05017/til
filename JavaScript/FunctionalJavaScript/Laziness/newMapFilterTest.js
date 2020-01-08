const rx = require("../rx/index.js")

console.log(rx.map(a => a + 10, rx.L.range(10)));
