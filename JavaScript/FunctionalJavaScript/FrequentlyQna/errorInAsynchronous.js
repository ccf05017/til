function errorInAsync(list) {
    try {
        return list
            .map(a => new Promise(resolve => {
                resolve(JSON.parse(a)); // 여기서 에러 발생
            }))
            .filter(a => a % 2)
            .slice(0, 2);
    } catch(e) {
        console.log(e); // 여기서 잡는 게 아니라
        return [];
    }
}

// console.log(errorInAsync(['0', '1', '2', '{']));

async function errorInAsyncAwait(list) {
    try {
        return await list
            .map(async a => await new Promise(resolve => {
                resolve(JSON.parse(a)); // 여기서 에러 발생
            }))
            .filter(a => a % 2)
            .slice(0, 2);
    } catch(e) {
        console.log(e); // 여기서 잡는 게 아니라
        return [];
    }
}

// console.log(errorInAsyncAwait(['0', '1', '2', '{']));

errorInAsyncAwait(['0', '1', '2', '{']).then(console.log).catch(e => {
    console.log('왜 안잡혀!');
});
