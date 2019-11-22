function doAsyncFunction(flag) {

    let err = flag;

    const promise = new Promise((resolve, reject) => {
        setTimeout(() => {
            if (err) {
                reject(err);
            } else {
                resolve("function success");
            }
        }, 1000);
    })

    return promise
}

// Success Case
doAsyncFunction(true).then(
    (successMessage) => {
        console.log("This is resolve handler");
        console.log(successMessage);
    },
    (err) => {
        console.log("This is reject handler");
        console.log(err);
    }
)

// Failure Case
// 아마 timeout 1초도 기다리지도 않고 err 보자마자 냅다 reject를 때려서 먼저 실행되는 듯(추측)
doAsyncFunction(false).then(
    (successMessage) => {
        console.log("This is resolve handler");
        console.log(successMessage);
    },
    (err) => {
        console.log("This is reject handler");
        console.log(err);
    }
)