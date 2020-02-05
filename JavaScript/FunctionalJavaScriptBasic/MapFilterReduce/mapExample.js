const rx = require('../rx/index.js');
const sampleData = require('../sampleData.js');

console.log("이름만 출력------------");
console.log(rx.map(product => product.name, sampleData.data));

console.log("가격만 출력------------");
console.log(rx.map(product => product.price, sampleData.data));

function* gen() {
    yield 2;
    yield 3;
    yield 4;
}

console.log(rx.map(a => a * a, gen()));

console.log("자료 타입 map도 적용 가능하다");
const sampleMap = new Map();
sampleMap.set('a', 10);
sampleMap.set('b', 20);

console.log(rx.map(([k, v]) => [k, v * 2], sampleMap));
