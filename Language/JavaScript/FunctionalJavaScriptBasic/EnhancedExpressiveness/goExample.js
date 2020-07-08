const rx = require('../rx/index.js');

rx.go(
    0,
    a => a + 1,
    a => a + 10,
    a => a + 100,
    console.log     // 함수를 값으로 전달하기 때문에 실행시키면 안된다.
);