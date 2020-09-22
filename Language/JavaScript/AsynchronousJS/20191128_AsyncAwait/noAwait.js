// 이렇게 해서는 그냥 일반적인 블로킹 함수일 뿐
// const printFunc1 = async () => {
//     console.log("1");
// }

// const printFunc2 = async () => {
//     console.log("2");
// }

// const main = async () => {
//     printFunc1();
//     printFunc2();
//     console.log("finished");
// }

// main(); // 1 -> 2 -> finished 출력

// 실제로 비동기 처리를 하려면 내부가 비동기로 구현되어 있어야 한다.
const printFunc1 = async () => {
    setImmediate(_ => console.log("1"));
}

const printFunc2 = async () => {
    console.log("2");
}

const main = async () => {
    printFunc1();
    printFunc2();
    console.log("finished");
}

main(); // 2 -> finished -> 1 순으로 출력된다. func1은 immediate 때문에 메인 루프와 다른 곳에서 처리