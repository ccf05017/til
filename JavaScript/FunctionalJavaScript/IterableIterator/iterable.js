const arr = [1, 2, 3];
const set = new Set([1, 2, 3]);
const map = new Map([['a', 1], ['b', 2], ['c', 3]]);

console.log(arr[Symbol.iterator]);   // [Function: values]
console.log(set[Symbol.iterator]);   // [Function: values]
console.log(map[Symbol.iterator]);   // [Function: entires] <- 얘는 좀 다르네?

console.log(arr[Symbol.iterator]());    // Object [Array Iterator] {}
console.log(set[Symbol.iterator]());    // [Set Iterator] { 1, 2, 3 }
console.log(map[Symbol.iterator]());    // [Map Iterator] { [ 'a', 1 ], [ 'b', 2 ], [ 'c', 3 ] }
