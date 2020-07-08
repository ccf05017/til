const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");

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

// 함수형1
function query2(obj) {
    return Object
        .entries(obj)
        .reduce((query, [k, v], i) => {
            if (v === undefined) return query;
            return `${query}${i > 0 ? '&' : ''}${k}=${v}`;
        }, '')
}

console.log(query2(obj1));

// 함수형2
const query3 = obj => 
    _.reduce((a, b) => `${a}&${b}`,
        _.map(([k, v]) => `${k}=${v}`, 
            _.reject(([_, v]) => v === undefined,
                Object.entries(obj)))
    );

console.log(query3(obj1));

// 함수형3
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
