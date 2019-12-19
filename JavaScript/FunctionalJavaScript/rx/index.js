exports.map = (f, iter) => {
    const result = [];
    // 예외 없이 모든 이터러블 요소에 적용됨
    for (const a of iter) {
        result.push(f(a));
    }
    return result;
};

exports.filter = (f, iter) => {
    const result = [];
    // 조건에 맞는 이터러블 요소에만 적용됨.
    for (const a of iter) {
        if (f(a)) result.push(a);
    }
    return result;
};

exports.reduce = (f, acc, iter) => {
    if (!iter) {
        // 시작값이 주어지지 않았을 때의 처리
        iter = acc[Symbol.iterator]();
        acc = iter.next().value;
    }
    for (const a of iter) {
        acc = f(acc, a);
    }
    return acc;
};