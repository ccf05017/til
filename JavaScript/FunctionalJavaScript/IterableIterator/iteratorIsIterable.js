const arr = [1, 2, 3];

console.log("첫번째 이터레이터");
const iter = arr[Symbol.iterator]();
console.log(iter);
console.log(iter.next());
console.log(iter.next());

console.log("두번째 이터레이터");
const iter2 = iter[Symbol.iterator]();
console.log(iter2);
console.log(iter2.next());