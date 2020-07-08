const rx = require("../rx/index.js")

const users = [
    { name: 'a', age: 21, family: [
        { name: 'a1', age: 53 }, { name: 'a2', age: 44 },
        { name: 'a3', age: 12 }, { name: 'a4', age: 22 },
    ] },
    { name: 'b', age: 24, family: [
        { name: 'b1', age: 51 }, { name: 'b2', age: 37 },
        { name: 'b3', age: 17 }, { name: 'b4', age: 15 },
    ] },
    { name: 'c', age: 27, family: [
        { name: 'c1', age: 57 }, { name: 'c2', age: 47 },
    ] },
    { name: 'd', age: 22, family: [
        { name: 'd1', age: 48 }, { name: 'd2', age: 22 },
        { name: 'd3', age: 19 }, { name: 'd4', age: 11 },
    ] },
];

const takeAll = rx.take(Infinity);

rx.go(
    users,
    rx.L.flatMap(u => u.family),    // user의 가족들만 가져옴
    rx.L.filter(f => f.age < 20),   // 가족 중 미성년만 골라냄
    rx.L.map(f => f.name),          // 이름만 빼냄
    rx.take(4),
    console.log
);
