# 이터러블 프로그래밍 혹은 리스트 프로세싱
## 1. 명령형 예제
- 홀수 n개 더하기를 해보자
- 명령형 예시
```js
function addOdd(limit, list) {

    let acc = 0;
    for (const a of list) {     // 반복수행
        if (a % 2) {
            const b = a * a;    // 값 변화 후 할당
            acc += b;       // 값 축약
        }
        if (--limit == 0) break;    // 효율화
    }
    console.log(acc);
}

addOdd(3, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);
```

- 위 코드가 나쁘다는 게 아님 충분히 효율적이고 잘 동작하는 코드임.
- 간단해 보이지만 프로그래밍의 거의 모든 것을 표현하고 있다.
- 즉, 이걸 함수형으로 전환해서 사고할 줄 알면 거의 모든 걸 함수형으로 할 수 있..을지도 모른다!

## 2. filter를 적용해보자
- if를 filter 함수로 전환해보자
```js
const FxJS = require("fxjs");

function addOddApplyFilter(limit, list) {

    let acc = 0;
    for (const a of FxJS.filterL(a => a % 2, list)) {
        // if (a % 2)
        const b = a * a;
        acc += b;
        if (--limit == 0) break;
    }
    console.log(acc);
}

addOddApplyFilter(3, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);
```

- 'FxJS.filterL(a => a % 2, list)'는 즉시 연산 되지 않는다.
- 인자로 전달된 필터의 조건으로 걸러질 '준비가 된' list가 '대기 중'인 상태로 읽어야 한다.

## 3. map을 적용해보자
- 값 변화 후 변수 할당하는 동작을 map으로 처리할 수 있다.
```js
const FxJS = require("fxjs");

function addOddApplyFilter(limit, list) {

    let acc = 0;
    for (const a of FxJS.mapL(a => a * a, FxJS.filterL(a => a % 2, list))) {
        // const b = a * a;
        acc += a;
        if (--limit == 0) break;
    }
    console.log(acc);
}

addOddApplyFilter(3, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);
```

- 명령형에서는 값을 변화시키고 따로 변수에 저장을 한 후 연산을 진행한다.
- 함수형에서는 filter 때와 마찬가지라 '값이 걸러지고, 변화될 예정인 이터러블'을 받아서 필요할 때 사용한다.

## 4. take를 적용해보자
- braek를 take로 전환할 수 있다.
- 전달받은 이터러블 중 앞에서부터 일부만 꺼낼 때 사용된다.
```js
const FxJS = require("fxjs");

function addOddApplyFilter(limit, list) {

    let acc = 0;
    for (const a of FxJS.takeL(limit, FxJS.mapL(a => a * a, FxJS.filterL(a => a % 2, list)))) {
        acc += a;
        // if (--limit == 0) break;
    }
    console.log(acc);
}

addOddApplyFilter(3, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);
```

## 5. reduce를 적용해보자
- 명령형에서 상위 스코프에 초기값을 놓고 계속해서 계산을 하거나 출력하는 부분을 대체 가능하다.
- 축약, 합산 등의 연산을 할 때 reduce를 사용할 수 있다.
- 흔히들 말하는 누산기 같은 것.
```js
function addOddApplyFilter(limit, list) {

    // 전부 대체 된다.
    // let acc = 0;
    // for (const a of FxJS.takeL(limit, FxJS.mapL(a => a * a, FxJS.filterL(a => a % 2, list)))) {
    //     acc += a;
    // }
    // console.log(acc);

    console.log(_.reduce(
        (acc, a) => acc + a,    // reduce(누산)을 수행할 함수
        0,                      // 초기값 (생략 가능하다. 생략되면 이터러블의 첫번째 계산된 값이 전달된다.)
        // 실행할 이터러블(계산이 적용 될 대기가 된 상태로 전달된다.)
        _.takeL(limit, 
            _.mapL(a => a * a, 
                _.filterL(a => a % 2, list))))
    );
}

addOddApplyFilter(3, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);
```

- 좀 더 짧게 가보자
```js

function addOddApplyFilter(limit, list) {

    const add = (a, b) => a + b;

    console.log(_.reduce(
        add,
        _.takeL(limit, 
            _.mapL(a => a * a, 
                _.filterL(a => a % 2, list))))
    );
}

addOddApplyFilter(3, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);
```

- 좀 더 사람이 말하는 것처럼 순서를 바꿔보자.
```js
function addOddApplyFilter(limit, list) {
    const add = (a, b) => a + b;

    _.go(list,
        L.filter(a => a % 2),
        L.map(a => a * a),
        L.take(limit),
        _.reduce(add),
        console.log
    );
}

addOddApplyFilter(3, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);
```

- 기존의 명령형과 완전히 다른 별천지가 아니다.
- 연관지어서 생각할 수 있고, 익숙함의 문제다.
- 좀 더 적응되면 훨씬 사람의 말과 유사하다고 느낄 것이다.

## 6. range를 적용해보자.
- 명령형의 while을 대체할 수 있다.
- 명령형 반복문 -> 주어진 때까지 이걸 반복하고 싶다는 의미.
- 함수형에서는 주어진 때를 범위로 이해한다.
```js
// 추상화 예시
// 아래 두 함수는 완전히 똑같이 동작한다.
function command(end) {
    let i = 0;
    while (i < end) {
        ++i;
    }
}

function functional(end) {
    L.range(end);
}
```

## 7. each를 적용해보자.
- 함수형에서는 외부와 순수 함수를 명확하게 구분하기를 원한다.
- each는 그 중 특이하게 '이건 부수효과(side-effect) 발생함'이라는 의미를 갖는다.
- 여기 오는 녀석들은 each 안에 있는 '효과'를 한명씩 맛보게 될 것이여!! 라는 의미
```js
function eachExample(end) {
    _.go(
        L.ragne(1, end, 2),

        // 저 위의 range 이터러블의 개별 요소는 모두 console.log의 영향을 받는다!!!
        // 라고 명확하게 명시하는 효과가 있다.
        _.each(console.log)
    );
}
```
