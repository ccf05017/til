function delay(a) {
    return new Promise(resolve => setTimeout(() => resolve(a), 500));
}

async function f1() {
    const a = await delay(10);
    console.log(a);
}

f1();

async function warn() {

    const a = await delay(10);
    const b = await delay(20);

    return a + b; // 30이 return 될 것으로 기대.
}

console.log(warn());    //Promise가 리턴 됨
warn().then(a => console.log(a));   // 사용하고 싶으면 이렇게 사용해야 함.

async function warn2() {

    const a = 10;
    const b = 20;

    return a + b;   // 30 return 기대
}

console.log(warn2());   // Promise가 리턴됨
warn2().then(a => console.log(a));