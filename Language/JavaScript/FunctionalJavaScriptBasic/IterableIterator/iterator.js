// Set, Map에 대해서도 아래의 방법을 적용할 수 있다.
const arr = [1, 2, 3];

const arrIterator = arr[Symbol.iterator]();

console.log(arrIterator.next());
console.log(arrIterator.next());
console.log(arrIterator.next());
console.log(arrIterator.next());
console.log(arrIterator.next());
