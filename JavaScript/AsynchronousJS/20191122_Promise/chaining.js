const promise = Promise.resolve("done");

promise.then(val => {

    console.log(val);
    return "done2"; // 다음 then으로 전달 가능하다.
}).then(val => {

    console.log(val);
})