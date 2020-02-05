const rx = require('../rx/index.js');
const sampleData = require('../sampleData.js');

const add = (a, b) => a + b;

console.log("상품의 가격만 뽑아내기");
console.log(rx.map(p => p.price, sampleData.data));

console.log("20000원 미만의 상품만 뽑아내기");
console.log(
    rx.map(p => p.price, 
        rx.filter(p => p.price < 20000, sampleData.data)));

console.log("20000원 미만인 상품의 가격 총합 구하기");
console.log(
    rx.reduce(
        add,
        0,
        rx.map(p => p.price,
            rx.filter(p => p.price < 20000, sampleData.data))));

console.log("20000원 미만인 상품의 가격 총합 구하기 ver2.");
console.log(
    rx.reduce(
        add,
        0,
        rx.filter(n => n < 20000,
            rx.map(p => p.price, sampleData.data))));