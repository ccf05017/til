function readFileFake(sleep) {
    return new Promise(resolve => setTimeout(resolve, sleep, "File read success."));
}

function timeOut(time) {
    return new Promise((_, reject) => setTimeout(reject, time, "timeout"));
}

const promises = [readFileFake(5000), timeOut(1000)];

Promise.race(promises)
    .then(val => console.log(val))
    .catch(err => console.log(err))
;

