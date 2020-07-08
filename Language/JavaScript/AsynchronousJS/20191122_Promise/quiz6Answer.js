function authenticate() {
    console.log("Authenticating");
    return new Promise(resolve => setTimeout(resolve, 2000, { status: 200 }));
}

function publish() {
    console.log("Publishing");
    return new Promise(resolve => setTimeout(resolve, 2000, { status: 403 }));
}

function timeout(sleep) {
    return new Promise((resolve, reject) => setTimeout(reject, sleep, "timeout"));
}

function safePublish() {
    return publish().then(res => {
        if (res.status === 403) {
            return authenticate();
        }
        return res;
    })
}

Promise.race([safePublish(), timeout(3000)])
    .then(_ => {
        console.log("Published");
    })
    .catch(err => {
        if (err === "timeout") {
            console.log("Request time out");
        } else {
            console.log(err);
        }
    })
;