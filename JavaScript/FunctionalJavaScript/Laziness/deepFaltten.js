const L = {};

const isIterable = a => a && a[Symbol.iterator];

L.deepFlat = function* f(iter) {
    for (const a of iter) {
        if (isIterable(a)) yield *f(a);
        else yield a;
    }
}

console.log(...L.deepFlat([1, [2, [3, 4], [5]]]));
