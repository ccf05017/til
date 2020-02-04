function* odds(limit) {
    for (let i = 0; i < limit; i++) {
        if (i % 2) yield i;
    }
}

const iter = odds(10);
console.log(iter.next());
console.log(iter.next());
console.log(iter.next());
console.log(iter.next());
console.log(iter.next());
console.log(iter.next());

// 명령형과 다르게 멈추거나 하지 않는다.
function* infinity(i = 0) {
    while (true) yield i++;
}

const iter2 = infinity();
console.log(iter2.next());
console.log(iter2.next());
console.log(iter2.next());
console.log(iter2.next());
console.log(iter2.next());
console.log(iter2.next());

function* odds(limit) {
    for (const a of infinity(1)) {
        if (a % 2) yield a;
        if (a == limit) return;
    }
}

const oddIter = odds(10);
console.log(oddIter.next());
console.log(oddIter.next());
console.log(oddIter.next());
console.log(oddIter.next());
console.log(oddIter.next());
console.log(oddIter.next());

function* limit(limit, iter) {
    for (const a of iter) {
        yield a;
        if (a == limit) return;
    }
}

function* oddWithLimit(l) {
    for (const a of limit(l, infinity(1))) {
        if (a % 2) yield a;
    }
}
const oddWithLimitIter = oddWithLimit(10);
console.log(oddWithLimitIter.next());
console.log(oddWithLimitIter.next());
console.log(oddWithLimitIter.next());
console.log(oddWithLimitIter.next());
console.log(oddWithLimitIter.next());
console.log(oddWithLimitIter.next());

// for...of도 가능하다
for (const a of oddWithLimit(40)) console.log(a);
