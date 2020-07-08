const rx = require('../rx/index.js');

function errorInPipeline(list) {
    try {
        return rx.go(list,
            rx.map(a => new Promise(resolve => {
                resolve(JSON.parse(a));
            })),
            rx.filter(a => a % 2),
            rx.take(2));
    } catch(e) {
        console.log(e);
        return [];
    }
}

// errorInPipeline(['0', '1', '2', '{']).then(console.log).catch(e => {
//     console.log(e);
//     console.log('이제 잡힌다!');
// });

async function errorInPipelineWithAsyncAwait(list) {
    try {
        return await rx.go(list,
            rx.map(a => new Promise(resolve => {
                resolve(JSON.parse(a));
            })),
            rx.filter(a => a % 2),
            rx.take(2));
    } catch(e) {
        console.log('in try/catch', e);
        return [];
    }
}

errorInPipelineWithAsyncAwait(['0', '1', '2', '{']).then(console.log).catch(e => {
    console.log('이제 잡힌다! in promise catch', e);
});
