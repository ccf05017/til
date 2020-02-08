# 명령형의 습관(잔재)들을 지우자
## reduce + 복잡한 함수 + acc < map + 간단한 함수 + reduce
- 리듀스는 굉장히 강력하다.
- 그래서 자꾸 리듀스만 써서 다 땜빵하려는 경향이 생긴다.
- 명령형의 잔재니까 없애는 게 좋다.
- 아래 코드는 간단해 보이지만 나중에 구현이 추가되면 굉장히 손 대기 어렵게 변화한다.
- 초기값과 입력되는 이터러블의 타입이 전혀 다르기 때문이다.
- 이렇게 되면 reduce가 매번 유저가 어떻게 생겼는지 확인하고 열어서 처리해야 한다.
```js
const users = [
    { name: 'AA', age: 1 },
    { name: 'BB', age: 2 },
    { name: 'CC', age: 3 },
    { name: 'DD', age: 4 },
    { name: 'EE', age: 5 }
];

console.log(_.reduce((total, u) => total + u.age, 0 , users));
```

- 즉, 보조함수는 최대한 간결하게 유지해라.
- 보조함수가 조금이라도 복잡해지면 나중에 아주 힘들어질 것이야!
- 아래 코드는 복잡성을 개선한 보조함수를 통해 구현
```js
console.log(_.reduce(
    (a, b) => a + b,
    L.map(user => user.age, users)));
```

## map이 부족하다면 filter도 데려오자
- 위의 방법으로도 연산이 더 필요할 수 있다.
- 이럴 땐 filter까지 갖고 와서 보조함수의 간결함을 잃지 말자
```js
// 명령형의 잔재
console.log(_.reduce(
    (total, u) => u.age >= 3 ? total + u.age : total,
    0,
    users));

console.log(_.reduce(
    (a, b) => a + b,
    L.map(u => u.age,
        L.filter(u => u.age >= 3, users))));
```
- 물론 여기에 go까지 끼어들면 훨씬 보기 좋다.
```js
// 명령형의 잔재
console.log(_.reduce(
    (total, u) => u.age >= 3 ? total + u.age : total,
    0,
    users));

console.log(_.reduce(
    (a, b) => a + b,
    L.map(u => u.age,
        L.filter(u => u.age >= 3, users))));
```