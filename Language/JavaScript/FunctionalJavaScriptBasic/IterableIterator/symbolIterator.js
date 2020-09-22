console.log('Array ----------------');
const arr = [1, 2, 3];
arr[Symbol.iterator] = null;
for (const a of arr) console.log(a); // 에러 발생(arr is not iterable)
