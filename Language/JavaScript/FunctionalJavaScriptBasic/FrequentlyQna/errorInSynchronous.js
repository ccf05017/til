function errorInSynchronous(list) {
    try {
        return list
            .map(a => a * a)
            .filter(a => a % 2)
            .slice(0, 2);
    } catch(e) {
        console.log(e);
        return [];
    }
}

console.log(errorInSynchronous(null));
