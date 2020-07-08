function* hello() {
    console.log(yield);
    console.log("World");   // 3. World 출력하면서 종료됨
}

const it = hello();
it.next();                  // 1. yield 이전까지만 실행됨
it.next("Hello");           // 2. yield에 Hello를 넘기면서 실행됨

// Hello -> World 로 출력된다.