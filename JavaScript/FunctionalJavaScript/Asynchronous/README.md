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
