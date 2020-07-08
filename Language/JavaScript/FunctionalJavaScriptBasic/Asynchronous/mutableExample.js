// 수학적인 상황에서는 아래 함수 실행 결과가 늘 같아야 한다.
const mutableArray = [1, 2, 3];

const f = a => a.map(r => r + 1);
const g = a => a.map(r => r * r);

console.log(f(g(mutableArray)));    // [2, 5, 10]

mutableArray.pop();
console.log(f(g(mutableArray)));    // [2, 5]
