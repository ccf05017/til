function addOdd(limit, list) {

    let acc = 0;
    for (const a of list) {     // 반복수행
        if (a % 2) {
            const b = a * a;    // 값 변화 후 할당
            acc += b;       // 값 축약
        }
        if (--limit == 0) break;    // 효율화
    }
    console.log(acc);
}

addOdd(3, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);
