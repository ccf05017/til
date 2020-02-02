const rx = require('../rx/index.js');

const delay500 = (a, name) => new Promise(resolve => {
    console.log(`${name}: ${a}`);
    setTimeout(() => resolve(a), 500);
});

// console.time('example1');
// rx.go([1, 2, 3, 4, 5, 6, 7, 8, 9],
//     rx.map(a => delay500(a * a, 'map 1')),
//     rx.filter(a => delay500(a % 2, 'filter 2')),
//     rx.map(a => delay500(a + 1, 'map 3')),
//     rx.take(2),
//     console.log,
//     _ => console.timeEnd('example1'));

// console.time('example2');
// rx.go([1, 2, 3, 4, 5, 6, 7, 8, 9],
//     rx.L.map(a => delay500(a * a, 'map 1')),
//     rx.L.filter(a => delay500(a % 2, 'filter 2')),
//     rx.L.map(a => delay500(a + 1, 'map 3')),
//     rx.take(4),
//     console.log,
//     _ => console.timeEnd('example2'));

// console.time('example3');
// rx.go([1, 2, 3, 4, 5, 6, 7, 8, 9],
//     rx.C.map(a => delay500(a * a, 'map 1')),
//     rx.L.filter(a => delay500(a % 2, 'filter 2')),
//     rx.L.map(a => delay500(a + 1, 'map 3')),
//     rx.take(4),
//     console.log,
//     _ => console.timeEnd('example3'));

console.time('example4');
rx.go([1, 2, 3, 4, 5, 6, 7, 8, 9],
    rx.L.map(a => delay500(a * a, 'map 1')),
    rx.L.filter(a => delay500(a % 2, 'filter 2')),
    rx.L.map(a => delay500(a + 1, 'map 3')),
    rx.C.take(4),
    console.log,
    _ => console.timeEnd('example4'));
