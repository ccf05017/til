const L = {};

const add3 = a => a + 3;

L.map = function* (f, iter) {
    for (const a of iter) yield f(a);
};

var it = L.map(add3, [1, 2, 3]);
console.log(it.next());
