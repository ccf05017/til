function add20(a) {
    return new Promise(resolve => setTimeout(() => resolve(a + 20), 2000));
}

add20(5).then(console.log);
