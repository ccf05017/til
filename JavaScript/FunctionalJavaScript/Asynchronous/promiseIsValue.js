function add10(a, callback)  {
    setTimeout(() => callback(a + 10), 1000);
}

const a = add10(5, res => {
    add10(res, () => {
        add10(res, () => {
            console.log(res);
        })
    })
});

console.log(a);     // undefined

function add20(a) {
    return new Promise(resolve => setTimeout(() => resolve(a + 20), 2000));
}

const b = add20(a).then(add20).then(add20).then(console.log);

console.log(b);     // Promise
