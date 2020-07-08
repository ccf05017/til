const _ = require("fxjs/Strict"); 
const L = require("fxjs/Lazy");

const example = [1, 2, 3, 4, 5, 6, 7, 8, 0, 0, 0];

_.go(
    example,
    _.takeWhile(a => a),
    _.each(console.log)
);

const example2 = [1, 2, 3, 4, 5, 6, 7, 8, 0, 10, 0];

_.go(
    example2,
    _.takeWhile(a => a),
    _.each(console.log)
);

_.go(
    example,
    _.takeUntil(a => !a),
    _.each(console.log)
);
