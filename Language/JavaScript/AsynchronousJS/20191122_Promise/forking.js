const promise = Promise.resolve("data");

// forking1
promise.then(val => {
    console.log(val);
    return "data2";
})

// forking2
promise.then(val => {
    console.log(val); // 위의 data2를 출력하지 않고 data를 출력함. 이걸 하고 싶으면 chaining 필요
})