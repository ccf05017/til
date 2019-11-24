// Promise의 비동기 처리
function doAsyncTask() {
    return Promise.resolve();
}

doAsyncTask().then(_ => console.log(promiseMessage));

const promiseMessage = "Promise Called";

// 콜백은 블로킹 방식으로 처리됨
// 이걸 비동기로 처리하고 싶으면 타임아웃 걸어줘야 한다.
// function doAsyncTask(cb) {
//     cb();
// }
// doAsyncTask(_ => console.log(callbackMessage));

// const callbackMessage = "Callback Called";