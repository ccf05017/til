const rx = require("../rx/index.js");
const sampleData = require("../sampleData.js");

const add = (a, b) => a + b;
const multi = rx.curry((a, b) => a * b);

// curry 동작 방식
console.log("커리 동작 방식");
console.log(multi);
console.log(multi(2));
console.log(multi(2)(4));

// curry를 이용한 평가 지연
console.log("커리를 통한 평가 지연 예시")
const multi3 = multi(3);
console.log(multi3(2));
console.log(multi3(3));
console.log(multi3(4));

// 읽기 쉬운 코드 만들기
console.log("go만 이용한 방식");
rx.go(
    sampleData.data,
    products => rx.filter(p => p.price < 20000, products),
    products => rx.map(p => p.price, products),
    prices => rx.reduce(add, prices),
    console.log
);

console.log("1차 축약");
rx.go(
    sampleData.data,
    products => rx.filter(p => p.price < 20000)(products),
    products => rx.map(p => p.price)(products),
    prices => rx.reduce(add)(prices),
    console.log
);

console.log("2차 축약");
rx.go(
    sampleData.data,
    rx.filter(p => p.price < 20000),
    rx.map(p => p.price),
    rx.reduce(add),
    console.log
);
