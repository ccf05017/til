const rx = require("../rx/index.js")

console.log("지연 평가 없는 버전");
rx.go(
    rx.range(10),
    rx.map(n => n + 10),
    rx.filter(n => n % 2),
    rx.take(2),
    console.log
);

console.log("지연 평가 있는 버전");
rx.go(
    rx.L.range(10),
    rx.L.map(n => n + 10),
    rx.L.filter
    (n => n % 2),
    rx.take(2),
    console.log
)
