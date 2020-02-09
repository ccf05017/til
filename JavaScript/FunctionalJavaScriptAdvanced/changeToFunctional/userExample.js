const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");

const users = [
    { name: 'AA', age: 1 },
    { name: 'BB', age: 2 },
    { name: 'CC', age: 3 },
    { name: 'DD', age: 4 },
    { name: 'EE', age: 5 }
];

console.log(_.reduce((total, u) => total + u.age, 0 , users));

console.log(_.reduce(
    (a, b) => a + b,
    L.map(user => user.age, users)));

// 명령형의 잔재
console.log(_.reduce(
    (total, u) => u.age >= 3 ? total + u.age : total,
    0,
    users));

console.log(_.reduce(
    (a, b) => a + b,
    L.map(u => u.age,
        L.filter(u => u.age >= 3, users))));
