# 자주 물어볼만한 질문들에 대해
- 유인동님이 자주 받으신 질문들에 대한 답변 모음집

## 1. 왜 FxJS의 map을 굳이 따로 쓰나요?
- map 외에 filter, reduce 등등도 포함되는 질문
- array 프로토타입에 있는 해당 함수들은 Promise 값을 계산할 수 없다.
- 기존의 프로토타입에 있는 함수들은 Promise를 제어하지 않는다.
- if, for 같은 명령형에 대해서만 동작한다.
```js
async function dealyI(a) {
    return new Promise(resolve => setTimeout(() => resolve(a), 100));
}

function f2() {
    const list = [1, 2, 3, 4, 5];
    const res = list.map(a => dealyI(a * a));
    console.log(res);
}

f2();   // Promise 뭉치만 선물로 줄 것
```

- async/await 조합으로 될 거 같은데요? -> 네 안됩니다.
```js
async function f3() {
    const list = [1, 2, 3, 4, 5];
    const res = await list.map(async a => await dealyI(a * a));
    console.log(res);
}

f3();
```

## 2. 비동기는 async/await로 하면 되는데 왜 파이프라인을 써야되요?
### 2.1 개요
- 둘의 해결하고자 하는 문제의 포인트가 전혀 다르다. (목표가 전혀 다르다)
- async/await: then, then을 계속 쓰기 어려우니 '문장형으로 비동기 상황을 다루기 위해' 만들어짐
- 파이프라인, 체이닝: 비동기 상황이 아니라 '안전한 함수 합성'을 위해 만들어짐
- 서로 1도 관련 없음

### 2.2 근데 왜 두개가 비슷하게 보여요?
- 착시.
- 파이프라인을 사용하면 비동기, 동기 상황을 비슷하게 다루게 된다.
- '이걸 보고 아 파이프라인은 비동기 상황을 동기적인 코드로 다루는구나!'로 오해하기 좋음


### 2.3 예시
- 아래 코드의 의도는 명령형이 아닌 안전한 방법으로 함수를 합성하는 게 목적이다.
```js
function f5() {
    
    return rx.go([1, 2, 3, 4, 5, 6, 7, 8, 9],
        rx.L.map(a => delayI(a * a)),
        rx.L.filter(a => delayI(a % 2)),
        rx.L.map(a => delayI(a + 1)),
        rx.take(3),
        rx.reduce((a, b) => delayI(a + b)));
}
```

- 그럼 명령형은 도대체 어떻게 되길래?
```js
// 길고 짜증나는 코드의 탄생
async function commandStyle(list) {

    let temp = [];

    // rx.L.map(a => delayI(a * a))
    for (const a of list) {
        const b = await delayI(a * a);

        // rx.L.filter(a => delayI(a % 2))
        if (await delayI(b % 2)) {

            // rx.L.map(a => delayI(a + 1))
            const c = b + 1;
            temp.push(c);

            // rx.take(3)
            if (temp.length == 3) break;
        }
    }

    // rx.reduce((a, b) => delayI(a + b)))
    let res = temp[0], i = 0;
    while (++i < temp.length) {
        res = await delayI(res + temp[i]);
    }
    return res;
}

rx.go(commandStyle(list), console.log);
```

- async/await는 위의 예제와 같이 사용하기 위한 코드

### 파이프라인의 이점
- 비동기 상황을 다룰 수 있는 것이지, 비동기 자체가 핵심 관심사가 아니다.
- 핵심 관심사는 함수의 '안전한 결합'
- 그렇기 때문에 주어진 delayI 함수가 동기 방식이건 비동기 방식이건 똑같이 사용할 수 있다.
- 심지어 명령형과 시간 복잡도도 같다.
- 좀 더 직관적이고 확신을 갖고 개발할 수 있도록 도와준다.
- 필요에 따라 병렬 처리가 필요할 때 파이프라인은 C 시리즈 함수를 사용하면 간단하게 해결 가능하다.
- 필요에 따라 동작을 추가하고 빼는 것도 아주 쉽다.

## 3. 그럼 async/await랑 파이프라인을 같이 쓰기도 하나요?
- 당연
- 내부에 비동기 상황 두개를 모아서 처리한다거나 할 때 사용하기 아주 좋다.
```js
async function delayI(a) {
    return new Promise(resolve => setTimeout(() => resolve(a), 100));
}

const list = [1, 2, 3, 4, 5, 6, 7, 8, 9];

async function pipeline(list) {
  
    const a = await rx.go(list,
        rx.L.map(a => delayI(a * a)),
        rx.L.filter(a => delayI(a % 2)),
        rx.L.map(a => delayI(a + 1)),
        rx.take(3),
        rx.reduce((a, b) => delayI(a + b)));

    const b = await rx.go(list,
        rx.L.map(a => delayI(a * a)),
        rx.take(3),
        rx.reduce((a, b) => delayI(a + b)));

    const c = await delayI(a + b)

    return c + 10;
};

rx.go(pipeline(list), console.log);
```

- 함수형을 통해 문장을 만들고, 이 두개를 명령형으로 최종 결합하는 식으로 사용하면 아주 쾌적하게 개발할 수 있다.

## 4. 동기 상황에서 에러 핸들링은 어떻게 해요?
- 함수 인자의 기본값을 주는 방법이 있다.
- 일단 받고 해당 값이 원하던 형태가 아닐때(처리할 수 없을 때) 기본값을 주는 방법이 있다.
- 일단 가장 기본적으로 사용하는 방법은 try/catch를 쓰는 것.
```js
function errorInSynchronous(list) {
    try {
        return list
            .map(a => a * a)
            .filter(a => a % 2)
            .slice(0, 2);
    } catch(e) {
        console.log(e);
        return [];
    }
}

console.log(errorInSynchronous(null));
```

## 5. 그럼 비동기 상황에서 에러 핸들링은 어떻게 해요?
- 기본적으로 동기 상황보다 다루기가 매우 까다롭다.
- 그냥 try/catch를 사용하면 Promise에서 나는 에러를 catch에서 잡기가 어렵다.
```js
function errorInAsync(list) {
    try {
        return list
            .map(a => new Promise(resolve => {
                resolve(JSON.parse(a)); // 여기서 에러 발생
            }))
            .filter(a => a % 2)
            .slice(0, 2);
    } catch(e) {
        console.log(e); // 여기서 잡는 게 아니라
        return [];
    }
}

console.log(errorInAsync(['0', '1', '2', '{']));
```

- 그럼 async/await로 비동기를 명령형으로 바꿔서 처리하면? => 응 안되
```js
async function errorInAsyncAwait(list) {
    try {
        return await list
            .map(async a => await new Promise(resolve => {
                resolve(JSON.parse(a)); // 여기서 에러 발생
            }))
            .filter(a => a % 2)
            .slice(0, 2);
    } catch(e) {
        console.log(e); // 여기서 잡는 게 아니라
        return [];
    }
}

console.log(errorInAsyncAwait(['0', '1', '2', '{']));
```

- 아 그러면 이 함수를 사용하는 쪽에서 catch로 잡으면 되잖아요! -> 응 안되(2)
```js
errorInAsyncAwait(['0', '1', '2', '{']).then(console.log).catch(e => {
    console.log('왜 안잡혀!');
});
```

- 이 문제의 발생 원인은 근본적으로 map, filter, reduce가 Promise 자체를 이해하지 못하기 때문에 발생한다.

## 그래서 파이프라인을 쓰면 좀 편합니다.
- FxJS의 함수들은 모두 비동기 상황을 지원한다.
- 위와 같이 비동기 상황을 따로 어렵게 에러처리 할 필요가 없다.
```js
function errorInPipeline(list) {
    try {
        return rx.go(list,
            rx.map(a => new Promise(resolve => {
                resolve(JSON.parse(a));
            })),
            rx.filter(a => a % 2),
            rx.take(2));
    } catch(e) {
        console.log(e);
        return [];
    }
}

errorInAsyncAwait(['0', '1', '2', '{']).then(console.log).catch(e => {
    console.log(e);
    console.log('이제 잡힌다!');
});
```

- 하지만 여전히 try/catch에서 에러 처리를 하진 않는다.
- 정 try/catch에서 처리하고 싶으면 return에서 Promise에서 reject가 발생해야 한다.
- async/await를 통해 바로 꺼낼 수 있다.
```js
async function errorInPipelineWithAsyncAwait(list) {
    try {
        return await rx.go(list,
            rx.map(a => new Promise(resolve => {
                resolve(JSON.parse(a));
            })),
            rx.filter(a => a % 2),
            rx.take(2));
    } catch(e) {
        console.log('in try/catch', e);
        return [];
    }
}

errorInPipelineWithAsyncAwait(['0', '1', '2', '{']).then(console.log).catch(e => {
    console.log('이제 잡힌다! in promise catch', e);
});
```

- 파이프라인에서는 잘 연결된 Promise를 전달해주기 때문에 에러 처리가 매우 쉽다.
- 추가로 지연 평가를 사용하면 평가를 뒤로 미루기 때문에 전체 이터러블 안에서 에러가 있는 부분에 가기 전까지는 정상 동작한다.
