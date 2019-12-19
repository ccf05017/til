exports.map = (f, iter) => {
    const result = [];
    for (const a of iter) {
        result.push(f(a));
    }
    return result;
}
