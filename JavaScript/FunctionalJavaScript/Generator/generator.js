function* gen() {
    yield 1;
    if (false) yield 2;
    yield 3;
    
    return 100;     // 마지막 순회 때 반환되는 값
}

const iterator = gen();

console.log(iterator[Symbol.iterator]());
console.log(iterator[Symbol.iterator]() == iterator);

console.log(iterator.next());       // 1
console.log(iterator.next());       // 3
console.log(iterator.next());       // 100
console.log(iterator.next());       // undefined

for (const a of gen()) console.log(a);  // 순회에서는 return 값 반환은 안함.
// 1, 3
