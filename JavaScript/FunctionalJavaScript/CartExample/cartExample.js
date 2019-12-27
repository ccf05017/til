const cartData = require("./cartData.js");
const rx = require("../rx/index.js");

const total_quantity = rx.pipe(
    rx.map(p => p.quantity),
    rx.reduce((a, b) => a + b)
);

console.log("총 수량 확인하기");
console.log(total_quantity(cartData.data));
