const iterable = {
    [Symbol.iterator]() {
        let i = 3;
        return {
            next() {
                return i == 0 ? { done: true } : { value: i--, done: false }
            }
        }
    }
};

// 이터레이터를 얻어서 수동 실행
const iterator = iterable[Symbol.iterator]();
console.log(iterator.next());
console.log(iterator.next());

// 이터러블/이터레이터 프로토콜을 통해 for...of 사용
for (const a of iterable) console.log(a);
