const g = a => a + 1;
const f = a => a * a;

// 정상적인 함수 합성
console.log(f(g(1)));       // 4

// 비정상적인 함수 합성
// 안전하지 않다.
console.log(f(g()));        //NaN

[1].map(g).map(f)                       // 함수 합성
    .forEach(r => console.log(r));      // 실제 사회에 현상 전달

[].map(g).map(f)                        // 함수 합성
    .forEach(r => console.log(r));      // 실제 사회에 현상 전달

Promise.resolve().then(g).then(f).then(r => console.log(r));

new Promise(resolve => 
    setTimeout(() => resolve(1), 1000)
).then(g).then(f).then(r => console.log(r));