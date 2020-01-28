const rx = require("../rx/index.js")

// 비동기 동작
rx.go([Promise.resolve(1), Promise.resolve(2), Promise.resolve(3)],
    rx.L.map(a => a + 10),
    rx.take(2),
    console.log);

// 중간에 Promise가 있어도 잘 작동함
rx.go([2, 3, 4],
    rx.L.map(a => Promise.resolve(a + 10)),
    rx.take(2),
    console.log);

// map만 써도 잘 작동함(map은 어차피 L.map과 takeAll의 조합)
rx.go([2, 3, 4],
    rx.map(a => Promise.resolve(a + 10)),
    console.log);

// 동기도 정상 작동
rx.go([1, 2, 3],
    rx.L.map(a => a + 10),
    rx.take(2),
    console.log);
