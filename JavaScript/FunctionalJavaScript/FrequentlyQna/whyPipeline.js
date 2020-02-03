const rx = require("../rx/index.js");

async function delayI(a) {
    return new Promise(resolve => setTimeout(() => resolve(a), 100));
}

function noDelay(a) {
    return a;
}

const list = [1, 2, 3, 4, 5, 6, 7, 8, 9];

function pipeline(list) {
    
    return rx.go(list,
        rx.L.map(a => delayI(a * a)),
        rx.L.filter(a => delayI(a % 2)),
        rx.L.map(a => delayI(a + 1)),
        rx.take(3),
        rx.reduce((a, b) => delayI(a + b)));
}

rx.go(pipeline(list), console.log);

function pipeline2(list) {
    
    return rx.go(list,
        rx.L.map(a => noDelay(a * a)),
        rx.L.filter(a => noDelay(a % 2)),
        rx.L.map(a => noDelay(a + 1)),
        rx.take(3),
        rx.reduce((a, b) => noDelay(a + b)));
}

rx.go(pipeline2(list), console.log);

async function commandStyle(list) {

    let temp = [];

    // rx.L.map(a => delayI(a * a))
    for (const a of list) {
        const b = await delayI(a * a);

        // rx.L.filter(a => delayI(a % 2))
        if (await delayI(b % 2)) {

            // rx.L.map(a => delayI(a + 1))
            const c = b + 1;
            temp.push(c);

            // rx.take(3)
            if (temp.length == 3) break;
        }
    }

    // rx.reduce((a, b) => delayI(a + b)))
    let res = temp[0], i = 0;
    while (++i < temp.length) {
        res = await delayI(res + temp[i]);
    }
    return res;
}

rx.go(commandStyle(list), console.log);
