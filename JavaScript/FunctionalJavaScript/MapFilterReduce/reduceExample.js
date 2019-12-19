const rx = require('../rx/index.js');
const sampleData = require('./sampleData.js');

// 명령형에서의 처리
const nums = [1, 2, 3, 4, 5];

let total = 0;
for (const n of nums) {
    total = total + n;
}
console.log(total);

// reduce를 통한 처리
const add = (a, b) => a + b;

console.log(rx.reduce(add, 0, nums));
// 보통 자바스크립트에서는 시작값을 생략해도 되도록 옵셔널하게 구현한다.
console.log(rx.reduce(add, nums));

console.log("주어진 상품의 가격 총합 구하기")
console.log(
    rx.reduce(
        (totalPrice, product) => totalPrice + product.price,
        0,
        sampleData.data
    )
)