const rx = require("../rx/index.js")

// 비동기 상황
rx.go([1, 2, 3, 4, 5, 6],
    rx.L.map(a => Promise.resolve(a * a)),
    rx.L.filter(a => a % 2),
    rx.L.map(a => a * a),
    rx.take(3),
    console.log);

// 동기 상황
rx.go([1, 2, 3, 4, 5, 6],
    rx.L.filter(a => a % 2),
    rx.L.map(a => a * a),
    rx.take(3),
    console.log);

rx.go([1, 2, 3, 4, 5, 6],
    rx.filter(a => a % 2),
    rx.map(a => a * a),
    rx.take(3),
    console.log);
