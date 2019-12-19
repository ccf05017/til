# 이터러블/이터레이터

## 1. array, set, map의 for ... of 알아보기(forOfExample.js)
### 1.1 각각의 for ... of 를 활용한 순회
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
### 1.2 Array와 Set의 차이점?
- Array는 a[index] 등의 용법으로 바로 접근이 가능하다.
- Set은 위처럼 인덱스 방식으로 직접 접근이 되지 않는다.
- 일반적인 명령형에서 제공되는 for 순회를 통해 index를 돌리면서 접근하는 건 아니라는 걸 알 수 있음

### 1.3 Set은 어떻게 반복문이 가능했나? (symbolIterator.js)
- Set은 내부에 Symbol.iterator라는 키로 접근할 수 있는 함수가 존재한다.
- 이는 Set이 이터러블이라는 의미이며, 이터러블/이터레이터 프로토콜이 적용 가능하다는 의미
```js
// 이런 방법으로 확인할 수 있다.
console.log(array[Symbol.iterator]); // f values() { [native code] }
```

- 이게 없으면 리스트 순회가 불가능함.
```js
console.log('Not Iterable Array ----------------');
const arr = [1, 2, 3];
arr[Symbol.iterator] = null;         // 이터러블이 아니게 파괴
for (const a of arr) console.log(a); // 에러 발생(arr is not iterable)
```

## 2. 이터러블/이터레이터 프로토콜
### 2.1 이터러블?(iterable.js)
- 이터레이터를 리턴하는 [Symbol.iterator]() 를 가진 '값'
```js
// Array, Map, Set은 각각 이렇게 생긴 '값'들을 갖고 있음
console.log(arr[Symbol.iterator]);   // [Function: values]
console.log(set[Symbol.iterator]);   // [Function: values]
console.log(map[Symbol.iterator]);   // [Function: entires] <- Map은 여러 종류의 이터레이터를 반환하기 때문

// 실행하면 이터레이터를 반환한다.
console.log(arr[Symbol.iterator]());    // Object [Array Iterator] {}
console.log(set[Symbol.iterator]());    // [Set Iterator] { 1, 2, 3 }
console.log(map[Symbol.iterator]());    // [Map Iterator] { [ 'a', 1 ], [ 'b', 2 ], [ 'c', 3 ] }
```

### 2.2 이터레이터?(iterator.js)
- { value, done } 객체를 리턴하는 next() 를 가진 '값'
- 이터레이터의 next()를 호출할 때마다 위의 객체를 리턴한다.
- done이 true가 될 때까지 계속 반복된다.
- done이 true가 된 이후의 next() 호출에 대해서는 { value: undefined, done: true }를 반환한다.
```js
// Set, Map에 대해서도 아래의 방법을 적용할 수 있다.
const arr = [1, 2, 3];

const arrIterator = arr[Symbol.iterator]();

console.log(arrIterator.next());    // { value: 1, done: false }
console.log(arrIterator.next());    // { value: 2, done: false }
console.log(arrIterator.next());    // { value: 3, done: false }
console.log(arrIterator.next());    // { value: undefined, done: true }
console.log(arrIterator.next());    // { value: undefined, done: true }
```

### 2.3 이터러블/이터레이터 프로토콜의 정의?
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

## 3. 사용자 정의 이터러블
- 기본적으로 [Symbol.iterator]()를 통해 이터레이터를 반환하도록 구현하기만 하면 된다.(customIterable.js)
```js
const iterable = {
    [Symbol.iterator]() {
        let i = 3;
        return {
            next() {
                return i == 0 ? { done: true } : { value: i--, done: false }
            }
        }
    }
};
```
- 하지만 제대로 된 구현을 위해서는 추가적인 기능이 필요하다.
- 이터레이터는 소모되다가 중간에 멈추고 나중에 다시 사용할 수 있어야 한다.
- 이터레이터는 자신을 반환하는 이터러블이어야 한다.
- 위의 조건을 모두 갖춘 이터러블/이터레이터를 'well-formed'라고 지칭한다. (wellFormedCustomIterable.js)
- node뿐 아니라 일반 브라우저 JS도 메서드들도 이터러블/이터레이터 프로토콜을 지원한다. (ex. querySelectorAll())

## 4. 전개 연산자
- 이 또한 이터러블/이터레이터 프로토콜을 지원한다.
```js
const a = [1, 2];
const arr = [3, 4, 5, 6];

a[Symbol.iterator]

log([...a, ...arr]);    // [1, 2, 3, 4, 5, 6]
```