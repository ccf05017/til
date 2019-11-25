new Promise((resolve, reject) => {
    throw "fail";
}).then(
    val => console.log(val) // 실행되지 않고 무시된다.
).then(
    val => console.log(val), // 실행되지 않고 무시된다.
    err => console.log(err) // throw만 처리하고 Promise 종료됨
)