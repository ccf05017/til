const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");

const users = [
    { name: 'AA', age: 35 },
    { name: 'BB', age: 25 },
    { name: 'CC', age: 15 },
    { name: 'DD', age: 5 },
    { name: 'EE', age: 45 },
]

const user = _.find(u => u.name == 'FF', users);
// console.log(user);      // undefiend

_.go(
    users,
    L.filter(u => u.name == "FF"),
    L.take(1),
    _.each(console.log)
);
