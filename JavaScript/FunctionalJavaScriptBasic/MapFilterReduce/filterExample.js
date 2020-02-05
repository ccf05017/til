const rx = require('../rx/index.js');
const sampleData = require('../sampleData.js');

// 일반적인 명령형에서의 필터
// map 때와 마찬가지로 매우 제한적인 사용만 가능하다.
// 조건이 바뀔 때마다 새로 구현을 해줘야 하기 때문
const under20000 = [];
for (const product of sampleData.data) {
    if (product.price < 20000) under20000.push(product);
}

// 조건 하나만 바뀌었는데도 바꿀 부분이 많다.
// 실수할 확률이 매우 높다.
const over20000 = [];
for (const product of sampleData.data) {
    if (product.price > 20000) over20000.push(product);
}

console.log(...under20000);
console.log(...over20000);

// 위의 명령형과 같은 결과를 아주 간단하게 얻어낼 수 있다.
console.log(...rx.filter(product => product.price < 20000, sampleData.data));
console.log(...rx.filter(product => product.price > 20000, sampleData.data));