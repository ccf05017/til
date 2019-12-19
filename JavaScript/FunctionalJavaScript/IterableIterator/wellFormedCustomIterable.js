const iterable = {
    [Symbol.iterator]() {
        let i = 3;
        return {
            next() {
                return i == 0 ? { done: true } : { value: i--, done: false }
            },
            [Symbol.iterator]() { return this; }
        }
    }
};

const iterator = iterable[Symbol.iterator]();
for (const a of iterable) console.log(a);
for (const a of iterator) console.log(a);   // well-formed가 아닐 경우 여기서 에러 발생
