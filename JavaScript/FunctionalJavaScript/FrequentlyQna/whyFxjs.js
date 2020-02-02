async function dealyI(a) {
    return new Promise(resolve => setTimeout(() => resolve(a), 100));
}

function f2() {
    const list = [1, 2, 3, 4, 5];
    const res = list.map(a => dealyI(a * a));
    console.log(res);
}

f2();   // Promise 뭉치만 선물로 줄 것

async function f3() {
    const list = [1, 2, 3, 4, 5];

    // 이 코드는 resolve 준비가 되지 않는 Promise만 던져준다.
    // FxJs 코드는 resolve 준비가 된 Promise를 던져준다.
    const temp = await list.map(async a => await dealyI(a * a));
    console.log(temp);
    const res = temp;
    console.log(res);
}

f3();
