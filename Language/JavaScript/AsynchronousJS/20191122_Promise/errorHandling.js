new Promise((resolve, reject) => {
    throw "fail";
}).then(
    val => console.log(val) // 실행되지 않고 무시된다.
).then(
    val => console.log(val), // 실행되지 않고 무시된다.
    err => console.log(`${err} catched by error handler`) // throw만 처리하고 Promise 종료됨
)

Promise.resolve("done")
    .then(val => {
        throw "fail";
    })
    .then(val => console.log(val))
    .catch(err => console.log(`${err} catched by catch`));