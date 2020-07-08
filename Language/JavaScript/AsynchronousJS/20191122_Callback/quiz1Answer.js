function doAsyncTask(cb) {

    // Answer1
    setImmediate(() => {
        console.log("*Answer1. - Use setImmediate()");
        console.log("Do callback asynchorously!");
        cb();
        console.log("\n");
    });

    // Answer2
    process.nextTick(() => {
        console.log("*Answer2. - Use process.nextTick()");
        console.log("Do callback asynchorously!");
        cb();
        console.log("\n");
    })
}

doAsyncTask(_ => console.log(message));

let message = "Callback Called";