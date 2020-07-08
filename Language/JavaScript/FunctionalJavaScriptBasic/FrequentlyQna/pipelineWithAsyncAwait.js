const rx = require("../rx/index.js");

async function delayI(a) {
    return new Promise(resolve => setTimeout(() => resolve(a), 100));
}

const list = [1, 2, 3, 4, 5, 6, 7, 8, 9];

async function pipeline(list) {
  
    const a = await rx.go(list,
        rx.L.map(a => delayI(a * a)),
        rx.L.filter(a => delayI(a % 2)),
        rx.L.map(a => delayI(a + 1)),
        rx.take(3),
        rx.reduce((a, b) => delayI(a + b)));

    const b = await rx.go(list,
        rx.L.map(a => delayI(a * a)),
        rx.take(3),
        rx.reduce((a, b) => delayI(a + b)));

    const c = await delayI(a + b)

    return c + 10;
};

rx.go(pipeline(list), console.log);
