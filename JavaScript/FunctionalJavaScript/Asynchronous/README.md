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
