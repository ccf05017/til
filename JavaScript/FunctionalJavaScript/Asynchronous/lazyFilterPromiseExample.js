const rx = require("../rx/index.js")

rx.go([1, 2, 3, 4, 5, 6],
    rx.L.map(a => Promise.resolve(a * a)),
    rx.L.filter(a => a % 2),
    rx.L.map(a => a * a),
    rx.take(3),
    console.log);
