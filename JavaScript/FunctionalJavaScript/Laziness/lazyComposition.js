const rx = require("../rx/index.js")

console.log("지연 평가 버전");
rx.go(
    rx.L.range(10),
    rx.L.map(n => n + 10),
    rx.L.filter(n => n % 2),
    rx.take(2),
    console.log
);

console.log("지연 평가 버전(무한수열)");
rx.go(
    rx.L.range(Infinity),
    rx.L.map(n => n + 10),
    rx.L.filter(n => n % 2),
    rx.take(2),
    console.log
);
