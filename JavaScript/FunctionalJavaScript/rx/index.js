exports.curry = f => 
    (a, ..._) => _.length ? f (a, ..._) : (..._) => f(a, ..._);

exports.L = {};

exports.C = {};

const nop = Symbol('nop'); // 아무 동작도 하지 않겠다는 Symbol 생성

function noop() {};

const catchNoop = arr => 
    (arr.forEach(a => a instanceof Promise ? a.catch(noop) : a), arr);

const isIterable = a => a && a[Symbol.iterator];

const isPromise = (a, f) => a instanceof Promise ? a.then(f) : f(a);

exports.take = this.curry((limit, iter) => {
    let res = [];
    iter = iter[Symbol.iterator]();
    return function recur() {
        let cur;
        while (!(cur = iter.next()).done) {
            const a = cur.value;
            
            if (a instanceof Promise) return a
            .then(a => {
                res.push(a);
                return res.length == limit ? res : recur();
            })
            .catch(e => e == nop ? recur() : Promise.reject(e));

            res.push(a);    // 이 부분은 Promise가 아닌 경우를 처리한다.
            if (res.length == limit) return res;
        }
        return res;
    }();
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
    // for (const a of iter) yield f(a);
    for (const a of iter) yield isPromise(a, f);
});

this.L.filter = this.curry(function* (f, iter) {
    // for (const a of iter) if (f(a)) yield a;    // Promise가 해결되서 넘어가야 함
    for (const a of iter) {
        const b = isPromise(a, f);
        // 비동기 상황을 실행하고 값이 있다면 Promise를 그대로 전달, 아니라면 nop 전달
        if (b instanceof Promise) yield b.then(b => b ? a : Promise.reject(nop));
        else if (b) yield a;
    }
});

this.L.flatten = function* (iter) {
    for (const a of iter) {
        if (isIterable(a)) for (const b of a) yield b;
        else yield a;
    }
}

this.L.deepFlat = function* f(iter) {
    for (const a of iter) {
        if (isIterable(a)) yield *f(a);
        else yield a;
    }
}

this.L.flatMap = this.curry(this.pipe(this.L.map, this.L.flatten));

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

const reduceF = (acc, a, f) => 
    a instanceof Promise ?
        a.then(
            a => f(acc, a),
            e => e == nop ? acc : Promise.reject(e)) :
        f(acc, a)

const head = iter => isPromise(this.take(1, iter), ([h]) => h);

exports.reduce = this.curry((f, acc, iter) => {

    // head를 통한 처리
    if (!iter) return this.reduce(f, head(iter = acc[Symbol.iterator]()), iter);

    iter = iter[Symbol.iterator]();

    // isPromise를 통해 첫 인자가 Promise일 경우도 안전하게 처리
    return isPromise(acc, function recur(acc) {
        let cur;
        while (!(cur = iter.next()).done) {
            acc = reduceF(acc, cur.value, f);

            // 인자로 전달된 함수가 Promise일 경우 재귀 동작
            if (acc instanceof Promise) return acc.then(recur);
        }
        return acc;
    });
    // let cur;
    // while (!(cur = iter.next()).done) {
    //     const a = cur.value;
    //     // acc = f(acc, a);
    //     // 약간 무식한 Promise 해결방법
    //     // acc = acc instanceof Promise ? acc.then(acc => f(acc, a)) : f(acc, a);
    // }
    // return acc;
});

exports.range = size => {
    let i = -1;
    let res = [];
    while(++i < size) {
        res.push(i);
    }
    return res;
};

exports.flatten = this.pipe(this.L.flatten, takeAll);

exports.flatMap = this.curry(this.pipe(this.L.map, this.flatten));

this.C.reduce = this.curry((f, acc, iter) => iter ? 
    this.reduce(f, acc, catchNoop([...iter])) :
    this.reduce(f, catchNoop([...acc]))
);

this.C.take = this.curry((limit, iter) => this.take(limit, catchNoop([...iter])));

this.C.takeAll = this.C.take(Infinity);

this.C.map = this.curry(this.pipe(this.L.map, this.C.takeAll));

this.C.filter = this.curry(this.pipe(this.L.filter, this.C.takeAll));
