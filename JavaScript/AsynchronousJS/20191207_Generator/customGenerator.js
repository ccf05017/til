function* range() {
    for (let i = 0; i < 10; i++) {
        yield Promise.resolve(i);
    }
}

// Promise가 출력됨
(async () => {
    for (let x of range()) {
        console.log(x);
    }
})();

// await를 통해 실제로 값을 받아서 출력
(async () => {
    for await (let x of range()) {
        console.log(x);
    }
})();