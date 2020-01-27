# 비동기: 동시성 프로그래밍
- callback(전통 방법), promise(메서드 체인), async/await(promise 기반)의 차이를 알아보자

## 1. callback(callback.js)
- 결과값을 실행할 함수를 전달함
```js
function add10(a, callback)  {
    setTimeout(() => callback(a + 10), 1000);
}

add10(5, res => {
    console.log(res);
});
```

## 2. Promise
- resolve 함수가 실행되면 동작 종료
- 만들어진 Promise를 전달한다 <- 이게 콜백과의 가장 큰 차이점
- 지연 실행이 가능하다.(즉시 실행되는 게 아니라 실행 준비된 Promise를 넘겨줌)
```js
function add20(a) {
    return new Promise(resolve => setTimeout(() => resolve(a + 20), 2000));
}

add20(5).then(console.log);

```
- 연속적으로 실행할 때 아주 쾌적하다. (콜백에겐 그 유명한 콜백헬이 있다.)
```js
add20(5).then(add20).then(add20).then(add20).then(console.log);
```

### 2.1 비동기를 값으로 처리하자!
- then, then, then으로 콜백헬 개선하는 게 주목적이 아니다.
- Promise를 통해 비동기 상황을 일급으로 처리할 수 있다.
- 비동기 상황 자체를 값으로 다룬다. (=비동기 '상황'을 코드로 다룰 수 있다)
- 함수에게 할당, 전달, 체이닝이 가능하다.
- 콜백은 실행한 후 아무것도 할 수 없다. (코드적인 컨텍스트만 처리함)
- Promise는 실행한 후에도 내가 원하는 값으로 다룰 수 있다.
```js
const a = add10(5, res => {
    add10(res, () => {
        add10(res, () => {
            console.log(res);
        })
    })
});
console.log(a);     // undefined

const b = add20(a).then(add20).then(add20).then(console.log);
console.log(b);     // Promise
```

### 2.2 Promise 값 활용
- 값이다. 일급이다. = 함수에게 전달 가능, 해당 값이 어떤 값인지 확인 할 수 있다.
- 아래 코드가 정상 동작하려면, go1의 파라미터 a, f 둘 다 동기적으로 즉시 실행 가능해야 한다.
- 즉 프로미스가 아닌 값이 들어와야 작동 가능하다는 의미.
```js
const go1 = (a, f) => f(a);
const add5 = a => a + 5;

console.log(go1(10, add5));
```

- 하지만 늘 뜻대로 되진 않지! a 인자가 시간이 걸리는 값이라면 어떨까!
- 행위 그 자체를 함수로 표현해서 보다 명확한 코드 작성이 가능하다.
```js
// 1000ms 뒤에 실행된다는 상황 자체를 명시적으로 표현함
const delay1000 = a => new Promise(resolve => 
    setTimeout(() => resolve(a), 1000));

const go1 = (a, f) => f(a);
const add5 = a => a + 5;

const result = go1(10, add5);
console.log(result);

const go2 = (a, f) => a instanceof Promise ? a.then(f) : f(a);

// 1000ms 뒤에 실행되는 상황을 add5 함수에 적용한다는 것을 명시적으로 표현
const result2 = go2(delay1000(10), add5)
result2.then(console.log);
```

## 3. 함수 합성
- 함수가 연속적으로 실행되는 것
- f(g(a)) 상황에서 a를 g가 계산하고 이 결과를 f에 전달해서 계산하는 것

### 3.1 모나드
- 함수 합성을 안전하게 수행하기 위한 개념이 '모나드'
- Promise는 비동기 상황을 안전하게 수행하는 모나드 구현체
- 동적 타입 언어에서는 직접적으로 모나드를 명시해서 사용하진 않는다.
- 함수 합성은 원하는대로 동작하지 않는 경우가 있다.
```js
const g = a => a + 1;
const f = a => a * a;

// 정상적인 함수 합성
console.log(f(g(1)));       // 4

// 비정상적인 함수 합성
// 안전하지 않다.
console.log(f(g()));        //NaN
```

- 모나드는 일종의 박스로 그 안에 인자를 담고, 모나드의 함수로 함수 합성을 진행한다.
```js
[1].map(g).map(f)                       // 함수 합성(여기서 array가 모나드)
    .forEach(r => console.log(r));      // 실제 사회에 현상 전달
```

- 이를 통해 안전하게 함수 합성이 가능하다.
```js
[].map(g).map(f)                        // 함수 합성
    .forEach(r => console.log(r));      // 실제 사회에 현상 전달
```
- 모나드는 사용자에게 무언가 값을 전달하기 위해 직접적인 역할을 하진 않는다.

### 3.2 모나드 관점의 Promise
- Promise의 resolve 함수, then 함수를 통해 함수 합성을 진행한다.
- 아래는 Array 모나드와 Promise 모나드의 유사성을 보여준다.
```js
Array.of(1).map(g).map(f).forEach(r => console.log(r));
Promise.resolve(1).then(g).then(f).then(r => console.log(r));
```

- 모나드는 용도별로 보장하는 안정성이 다르다.
- Promise 모나드는 값에 대한 안정성이 아닌, '비동기 상황'의 안정성을 제공한다.
```js
// 이상한 값이 인자로 전달되면 이상하게 작동한다.
// 값에 대한 건 관심사가 아니기 때문
Promise.resolve().then(g).then(f).then(r => console.log(r));

// 대신 Promise는 언제 실행되건 안정된 결과를 전달해준다.
new Promise(resolve => 
    setTimeout(() => resolve(1), 1000)
).then(g).then(f).then(r => console.log(r));
```

## 4.Kleisli Composition
- 오류가 발생 가능한 상황에서 함수 합성을 안전하게 처리 가능하게 해주는 규칙

### 4.1 수학적 불변성
- 일반적으로 수학에선 'f(g(x)) = f(g(x))' 상황이 무조건 참이다.
- 하지만 프로그래밍에선 g가 참조하던 변수가 변화하면 위의 상황을 참이라고 보장할 수 없다.(mutableExample.js)
```js
// 수학적인 상황에서는 아래 함수 실행 결과가 늘 같아야 한다.
const mutableArray = [1, 2, 3];

const f = a => a.map(r => r + 1);
const g = a => a.map(r => r * r);

console.log(f(g(mutableArray)));    // [2, 5, 10]

mutableArray.pop();
console.log(f(g(mutableArray)));    // [2, 5]
```

### 4.2 현대 프로그래밍
- 외부 인자로부터 독립적인 함수 작성은 실질적으로 불가능하다.
- Kleisli Composition은 이러한 현대 프로그래밍 상황을 해결하기 위한 함수 합성의 방법
- Kleisli Composition은 오류 상황을 한정으로 'f(g(x)) = g(x)'를 성립하도록 하는 규칙이다.
```js
const users = [
    { id: 1, name: 'aa'},
    { id: 2, name: 'bb'},
    { id: 3, name: 'cc'},
]

const getUserById = id => 
    users.find(u => u.id == id) || Promise.reject("empty!");

const f = ({name}) => name;
const g = getUserById;

const safeFg = id => Promise.resolve(id).then(g).then(f).catch(a => a);

safeFg(2).then(console.log);        // 이 부분은 원래 실행 가능한데 아래와 똑같이 에러가 발생한다.(불변성 보장)

// 안전하지 못한 상황을 강제로 만들어낸다.
users.pop();
users.pop();

safeFg(2).then(console.log);        // f가 실행되지 않고 g의 에러만 나온다.
```

## 5. go, pipe, reduce에서 비동기 제어
- 현재의 go 함수는 reduce가 모든 제어권을 갖고 있음
- pipe 함수도 go 함수가 모든 제어권을 갖고 있음
- 결론적으로 reduce만 잘 수정해주면 됨
```js
const isPromise = (a, f) => a instanceof Promise ? a.then(f) : f(a);

exports.reduce = this.curry((f, acc, iter) => {
    if (!iter) {
        // 시작값이 주어지지 않았을 때의 처리
        iter = acc[Symbol.iterator]();
        acc = iter.next().value;
    } else {
        iter = iter[Symbol.iterator]();
    }

    return isPromise(acc, function recur(acc) {
        let cur;
        while (!(cur = iter.next()).done) {
            const a = cur.value;
            acc = f(acc, a);

            // 인자로 전달된 함수가 Promise일 경우 재귀 동작
            if (acc instanceof Promise) return acc.then(recur);
        }
        return acc;
    });
});
```

## 6. Promise.then()의 규칙
- then()을 통해 실행된 Promise는 절대 Promise를 return 해서는 안된다.
- Promise chain이 얼마나 이어지건 마지막에 then 한방으로 원하는 값을 받아낼 수 있다.
