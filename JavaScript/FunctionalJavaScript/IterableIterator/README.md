# 이터러블/이터레이터

## array, set, map for ... of 알아보기(forOfExample.js)
### 각각의 for ... of 를 활용한 순회
- 셋 모두 for ... of를 통해 순회가 가능하다
```js
console.log('Array ----------------');
const arr = [1, 2, 3];
for (const a of arr) console.log(a);

console.log('Set ----------------')
const set = new Set[1, 2, 3];
for (const a of set) console.log(a);

console.log('Map ----------------')
const map = new Map([['a', 1], ['b', 2], ['c', 3]]);
for (const a of map) console.log(a);
```
### 차이점?
- Array는 a[index] 등의 용법으로 바로 접근이 가능하다.
- Set은 위처럼 인덱스 방식으로 직접 접근이 되지 않는다.
- 일반적인 명령형에서 제공되는 for 순회를 통해 index를 돌리면서 접근하는 건 아니라는 걸 알 수 있음

### 그렇다면 어떻게?(symbolIterator.js)
- Array, Map, Set은 내부에 Symbol.iterator라는 키로 접근할 수 있는 함수가 존재한다.
```js
// 이런 방법으로 확인할 수 있다.
console.log(array[Symbol.iterator]); // f values() { [native code] }
```

- 이게 없으면 리스트 순회가 불가능함.
```js
console.log('Not Iterable Array ----------------');
const arr = [1, 2, 3];
arr[Symbol.iterator] = null;
for (const a of arr) console.log(a); // 에러 발생(arr is not iterable)
```

## 이터러블/이터레이터 프로토콜
### 이터러블?(iterable.js)
- 이터레이터를 리턴하는 [Symbol.iterator]() 를 가진 '값'
```js
// Array, Map, Set은 각각 이렇게 생긴 '값'들을 갖고 있음
console.log(arr[Symbol.iterator]);   // [Function: values]
console.log(set[Symbol.iterator]);   // [Function: values]
console.log(map[Symbol.iterator]);   // [Function: entires] <- Map은 여러 종류의 이터레이터를 반환하기 때문(프로토콜 설명 참조)

// 실행하면 이터레이터를 반환한다.
console.log(arr[Symbol.iterator]());    // Object [Array Iterator] {}
console.log(set[Symbol.iterator]());    // [Set Iterator] { 1, 2, 3 }
console.log(map[Symbol.iterator]());    // [Map Iterator] { [ 'a', 1 ], [ 'b', 2 ], [ 'c', 3 ] }
```

### 이터레이터?(iterator.js)
- { value, done } 객체를 리턴하는 next() 를 가진 '값'
- 이터레이터의 next()를 호출할 때마다 위의 객체를 리턴한다.
- done이 true가 될 때까지 계속 반복된다.
- done이 true가 된 이후의 next() 호출에 대해서는 { value: undefined, done: true }를 반환한다.
```js
// Set, Map에 대해서도 아래의 방법을 적용할 수 있다.
const arr = [1, 2, 3];

const arrIterator = arr[Symbol.iterator]();

console.log(arrIterator.next());
console.log(arrIterator.next());
console.log(arrIterator.next());
console.log(arrIterator.next());
console.log(arrIterator.next());

```

### 이터러블/이터레이터 프로토콜의 정의?
- '이터러블'을 for ... of, 전개 연산자 등과 함께 동작하도록 한 '규약'
- 말 그대로 규약이기 때문에 이터러블을 정상적으로 구현한 어떤 객체든 for ... of, 전개 연산자 사용이 가능하다.
- 규약을 지키는 함수를 정의하여 모든 이터러블에 대해 사용하도록 만드는 것도 가능하다.
- for ... of 의 경우 이터레이터의 next()가 반환하는 done이 false일 동안, value에 반복적으로 접근해준다.
- Map의 경우 Set, Array와 약간 다르다.(mapProtocol.js)
```js
const map = new Map([['a', 1], ['b', 2], ['c', 3]]);

console.log(map.values());   // value '이터레이터' 반환
console.log(map.keys());     // key '이터레이터' 반환
console.log(map.entries());  //  entries '이터레이터' 반환

console.log("Only Values--------------------")
for (const a of map.values()) console.log(a);

console.log("Only Keys----------------------")
for (const a of map.keys()) console.log(a);

// 일반적으로 map을 바로 이터레이터로 사용하면 이걸 반환한다.
console.log("Full Map-----------------------")
for (const a of map.entries()) console.log(a);
```
- 이터레이터 또한 이터러블이다. (iteratorIsIterable.js)
```js
// 이 무슨 해괴한 소리인가..
const arr = [1, 2, 3];

console.log("첫번째 이터레이터");
const iter = arr[Symbol.iterator]();
console.log(iter);
console.log(iter.next());
console.log(iter.next());

console.log("두번째 이터레이터");
const iter2 = iter[Symbol.iterator]();  // 이터레이터는 또 이터레이터를 리턴한다. => 이터러블이다.
console.log(iter2);
console.log(iter2.next());
```