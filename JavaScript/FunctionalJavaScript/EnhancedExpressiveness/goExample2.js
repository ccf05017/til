const rx = require('../rx/index.js');
const sampleData = require('../sampleData.js');

const add = (a, b) => a + b;

console.log("20000원 미만인 상품의 가격 총합 구하기 (original)");
console.log(
    rx.reduce(
        add,
        0,
        rx.map(p => p.price,
            rx.filter(p => p.price < 20000, sampleData.data))));

console.log("20000원 미만인 상품의 가격 총합 구하기 (go사용)");
rx.go(
    sampleData.data,
    products => rx.filter(p => p.price < 20000, products),
    products => rx.map(p => p.price, products),
    prices => rx.reduce(add, prices),
    console.log
)