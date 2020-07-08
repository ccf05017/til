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

## 보조 함수를 간단하게 유지하는 이점 예제1
- 객체를 URL 쿼리 형태로 전환하는 연습을 해보자
- 명령형 예제
```js
const obj1 = {
    a: 1,
    b: undefined,
    c: 'CC',
    d: 'DD'
};

// trans to 'a=1&c=CC&d=DD'

// 명령형
function query1(obj) {
    let res = '';
    for (const k in obj) {
        const v = obj[k];
        if (v === undefined) continue;
        if (res != '') res += '&';  // 사이에 &를 넣기 위한 억지 동작
        res += k + '=' + v;
    }
    return res;
}

console.log(query1(obj1));
```

- 반복문 내부에 예외 처리가 여러개 있다.(약간 복잡함)
- 이런걸 그냥 reduce에 부어넣으면 복잡도를 줄이기 어렵다.
```js
function query2(obj) {
    return Object
        .entries(obj)
        .reduce((query, [k, v], i) => {
            if (v === undefined) return query;
            return `${query}${i > 0 ? '&' : ''}${k}=${v}`;
        }, '')
}

console.log(query2(obj1));
```

- map, filter(reject)를 결합하면 더 쉬워진다.
```js
const query3 = obj => 
    _.reduce((a, b) => `${a}&${b}`,
        _.map(([k, v]) => `${k}=${v}`, 
            _.reject(([_, v]) => v === undefined,
                Object.entries(obj)))
    );

console.log(query3(obj1));
```

- go 함수로 가독성을 더 높여줄 수 있다.
```js
const join = _.curry((sep, iter) => 
    _.reduce((a, b) => `${a}${sep}${b}`, iter));

const query4 = obj => _.go(
    obj,
    Object.entries,
    _.reject(([_, v]) => v === undefined),
    _.map(([k, v]) => `${k}=${v}`),
    join('&')
);

console.log(query4(obj1));
```

## 보조 함수를 간단하게 유지하는 이점 예제1
- queryString을 반대로 객체로 변환하는 연습을 해보자
- 자바스크립트의 내장된 문자열 split 메소드를 활용한.
- 이걸 파이프라인에서 쓰기 위해 curry로 묶어서 함수로 만든 뒤 재 사용한다.
- Object.assign 내장함수를 활용한다.
- 연속된 객체를 묶어준다. 객체계의 sum 함수다.
```js
const split = _.curry((sep, str) => str.split(sep));

const queryToObj = _.pipe(
    split('&'),
    _.map(split('=')),
    _.map(([k, v]) => ({ [k]: v })),
    _.reduce(Object.assign)
);

console.log(queryToObj('a=1&c=CC&d=DD'));
```

- 명령형 습관대로 했다면 아마 map 함수 두개 없이 그냥 들어갔을 확률이 높다.
- 그러면 굉장히 복잡하다. 이렇게 단순한 함수를 결합해서 만들기 위해 노력하자.
