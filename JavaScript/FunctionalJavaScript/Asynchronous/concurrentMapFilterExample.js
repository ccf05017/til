const rx = require("../rx/index.js");

const delay1000 = a => new Promise(resolve => 
    setTimeout(resolve(a), 1000));

rx.C.map(a => delay1000(a * a), [1, 2, 3, 4, 5]).then(console.log);

rx.C.filter(a => delay1000(a % 2), [1, 2, 3, 4, 5]).then(console.log);
