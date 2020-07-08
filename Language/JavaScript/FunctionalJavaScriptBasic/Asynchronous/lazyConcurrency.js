const rx = require("../rx/index.js");

// 지연이 이만큼 걸리는 작업이 있다 가정하자.
const delay1000 = a => new Promise(resolve => 
    setTimeout(() => resolve(a), 1000));

const add = (a, b) => a + b;

// 병렬 처리 안할 경우 5014.916ms
console.time('no concurrency');
rx.go([1, 2, 3, 4, 5],
    rx.L.map(a => delay1000(a * a)),
    rx.L.filter(a => delay1000(a % 2)),
    rx.L.map(a => delay1000(a * a)),
    rx.reduce(add),
    console.log,
    _ => console.timeEnd('no concurrency'));

// 병렬 처리 1009.993ms
console.time('concurrency');
rx.go([1, 2, 3, 4, 5],
    rx.L.map(a => delay1000(a * a)),
    rx.L.filter(a => delay1000(a % 2)),
    rx.L.map(a => delay1000(a * a)),
    rx.C.reduce(add),
    console.log,
    _ => console.timeEnd('concurrency'));

// 병렬처리 take
console.time('concurrency take');
rx.go([1, 2, 3, 4, 5, 6, 7, 8, 9],
    rx.L.map(a => delay1000(a * a)),
    rx.L.filter(a => delay1000(a % 2)),
    rx.L.map(a => delay1000(a * a)),
    rx.C.take(3),
    console.log,
    _ => console.timeEnd('concurrency take'));
