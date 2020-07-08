const FxJS = require("fxjs");

function addOddApplyFilter(limit, list) {

    let acc = 0;
    for (const a of FxJS.filterL(a => a % 2, list)) {
        // if (a % 2)
        const b = a * a;
        acc += b;
        if (--limit == 0) break;
    }
    console.log(acc);
}

addOddApplyFilter(3, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);
