const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");

const split = _.curry((sep, str) => str.split(sep));

const queryToObj = _.pipe(
    split('&'),
    L.map(split('=')),
    L.map(([k, v]) => ({ [k]: v })),
    _.reduce(Object.assign)
);

console.log(queryToObj('a=1&c=CC&d=DD'));
