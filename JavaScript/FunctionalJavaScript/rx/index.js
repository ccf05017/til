exports.curry = f => 
    (a, ..._) => _.length ? f (a, ..._) : (..._) => f(a, ..._);

exports.L = {};

exports.take = this.curry((limit, iter) => {
    let res = [];
    for (const a of iter) {
        res.push(a);
        if (res.length == limit) return res;
    }
    return res;
});

exports.go = (...args) => this.reduce((a, f) => f(a), args);

exports.pipe = (f, ...fs) => (...as) => this.go(f(...as), ...fs);

const takeAll = this.take(Infinity);

this.L.range = function* (size) {
    let i = -1;
    while(++i < size) {
        yield i;
    }
};

this.L.map = this.curry(function* (f, iter) {
    for (const a of iter) yield f(a);
});

this.L.filter = this.curry(function* (f, iter) {
    for (const a of iter) if (f(a)) yield a;
});

// exports.map = this.curry((f, iter) => {
//     const result = [];
//     // 예외 없이 모든 이터러블 요소에 적용됨
//     // for (const a of iter) {
//     //     result.push(f(a));
//     // }
//     // 위의 반복문 명령형으로 전환한 코드 -> 아래 다른 코드들도 이렇게 구현할 수 있다.
//     iter = iter[Symbol.iterator]();
//     let cur;
//     while(!(cur = iter.next()).done) {
//         const a = cur.value;
//         result.push(f(a));
//     }
//     return result;
// });

// 게으른 map을 통한 map 재구현
exports.map = this.curry(this.pipe(this.L.map, takeAll));

// exports.filter = this.curry((f, iter) => {
//     const result = [];
//     // 조건에 맞는 이터러블 요소에만 적용됨.
//     for (const a of iter) {
//         if (f(a)) result.push(a);
//     }
//     return result;
// });

// 게으른 filter을 통한 filter 재구현
exports.filter = this.curry(this.pipe(this.L.filter, takeAll));

exports.reduce = this.curry((f, acc, iter) => {
    if (!iter) {
        // 시작값이 주어지지 않았을 때의 처리
        iter = acc[Symbol.iterator]();
        acc = iter.next().value;
    }
    for (const a of iter) {
        acc = f(acc, a);
    }
    return acc;
});

exports.range = size => {
    let i = -1;
    let res = [];
    while(++i < size) {
        res.push(i);
    }
    return res;
};
