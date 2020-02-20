const _ = require("fxjs/Strict"); 
const L = require("fxjs/Lazy");

// 필요한 만큼만 계산하기 때문에 쓸데 없는 지연이 추가로 발생하지 않는다. (지연시간: 3sec)
_.go(
    L.range(10),
    L.map(_.delay(1000)),
    L.take(3),
    _.each(console.log)
);
