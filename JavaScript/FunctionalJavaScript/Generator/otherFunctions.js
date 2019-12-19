function* infinity(i = 0) {
    while (true) yield i++;
}

function* limit(l, iter) {
    for (const a of iter) {
        yield a;
        if (a == l) return;
    }
}

function* odds(l) {
    for (const a of limit(l, infinity(1))) {
        if (a % 2) yield a;
    }
}

console.log("전개 연산자");
console.log(...odds(10));

console.log("구조 분해");
const [head, ...tail] = odds(10);
console.log(head);
console.log(tail);

console.log("나머지 연산자");
const [a, b, ...rest] = odds(10);
console.log(a);
console.log(b);
console.log(rest);
