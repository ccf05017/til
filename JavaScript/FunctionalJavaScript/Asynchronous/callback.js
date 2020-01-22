function add10(a, callback)  {
    setTimeout(() => callback(a + 10), 1000);
}

add10(5, res => {
    console.log(res);
});
