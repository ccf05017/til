const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");

const users = [
    { id: 5, name: 'AA', age: 35 },
    { id: 10, name: 'BB', age: 26 },
    { id: 19, name: 'CC', age: 28 },
    { id: 23, name: 'DD', age: 34 },
    { id: 24, name: 'EE', age: 23 },
]

const indexBy = (f, itr) => 
    _.reduce((obj, a) => (obj[f(a)] = a, obj), {}, itr);

const users2 = indexBy(u => u.id, users);

console.log(users2);

_.go(
    users2,
    _.entries,
    _.filter(([_, { age }]) => age > 30),
    _.object,
    console.log
);
