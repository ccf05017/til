const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");

function addOddApplyFilter(limit, list) {

    // 전부 대체 된다.
    // let acc = 0;
    // for (const a of FxJS.takeL(limit, FxJS.mapL(a => a * a, FxJS.filterL(a => a % 2, list)))) {
    //     acc += a;
    // }
    // console.log(acc);

    const add = (a, b) => a + b;

    // console.log(_.reduce(
    //     add,    // reduce(누산)을 수행할 함수(외부에서 함수를 가져와도 된다.)
    //     0,                      // 초기값 (생략 가능하다. 생략되면 이터러블의 첫번째 계산된 값이 전달된다.)
    //     // 실행할 이터러블(계산이 적용 될 대기가 된 상태로 전달된다.)
    //     _.takeL(limit, 
    //         _.mapL(a => a * a, 
    //             _.filterL(a => a % 2, list))))
    // );

    _.go(list,
        L.filter(a => a % 2),
        L.map(a => a * a),
        L.take(limit),
        _.reduce(add),
        console.log
    );
}

addOddApplyFilter(3, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);
