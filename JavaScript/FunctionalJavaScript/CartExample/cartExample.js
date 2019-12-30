const cartData = require("./cartData.js");
const rx = require("../rx/index.js");

const add = (a, b) => a + b;

// 아래 total_quantity, total_price를 추상화 시킴
const sum = rx.curry((f, iter) => rx.go(
    iter,
    rx.map(f),
    rx.reduce(add)
));

const total_quantity = sum(p => p.quantity);

const total_price = sum(p => p.quantity * p.price);

console.log("총 수량 확인하기");
console.log(total_quantity(cartData.data));

console.log("총 합산 금액 확인하기");
console.log(total_price(cartData.data));

console.log("번외편: 모든 사람의 나이 더하기");
const total_age = sum(person => person.age);
console.log(total_age([
    {age: 10},
    {age: 20},
    {age: 30}
]));
