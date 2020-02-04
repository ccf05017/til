const rx = require("../rx/index.js");

rx.go(Promise.resolve(1),
  a => a + 10,
  a => Promise.reject('error!'),
  a => a + 1000,
  a => a + 10000,
console.log).catch(e => console.log(e));