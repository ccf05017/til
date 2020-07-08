const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");

// 결과물
// *
// **
// ***
// ****
// *****

// 중첩을 계속 명시하면서 표현하기.
_.go(
    L.range(1, 6),
    L.map(L.range),
    L.map(L.map(_ => '*')),
    L.map(_.reduce((a, b) => `${a}${b}`)),
    _.reduce((a, b) => `${a}\n${b}`),
    console.log
);

// 중첩된 함수를 내부에서 모두 처리하고 빠져나오기.
_.go(
    L.range(1, 6),
    L.map(idx => _.go(
        L.range(idx),
        L.map(_ => '*'),
        _.reduce((a, b) => `${a}${b}`)
    )),
    _.reduce((a, b) => `${a}\n${b}`),
    console.log
);

// 중첩된 함수를 따로 함수로 빼서 생각하고 안에 쑤셔넣기
const innerFunction = idx => _.go(
    L.range(idx),
    L.map(_ => '*'),
    _.reduce((a, b) => `${a}${b}`)
);

_.go(
    L.range(1, 6),
    L.map(innerFunction),
    _.reduce((a, b) => `${a}\n${b}`),
    console.log
);
