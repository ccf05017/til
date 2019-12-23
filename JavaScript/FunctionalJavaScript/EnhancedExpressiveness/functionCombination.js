const rx = require("../rx/index.js");
const sampleData = require("../sampleData.js");

const add = (a, b) => a + b;
const total_price = rx.pipe(
    rx.map(p => p.price),
    rx.reduce(add),
);
const base_total_price = predicate => rx.pipe(
    rx.filter(predicate),
    total_price
)

console.log("20000원 미만인 상품의 가격 총합 구하기");
rx.go(
    sampleData.data,
    base_total_price(p => p.price < 20000),
    console.log
);

console.log("20000원 이상인 상품의 가격 총합 구하기");
rx.go(
    sampleData.data,
    base_total_price(p => p.price >= 20000),
    console.log
);
