const _ = require("fxjs/Strict"); 
const L = require("fxjs/Lazy");

const track = [
    { cars: ['철수', '영희', '철희', '영수'] },
    { cars: ['하든', '커리', '듀란트', '탐슨'] },
    { cars: ['폴', '어빙', '릴라드', '맥컬럼'] },
    { cars: ['마리오', '루이지'] },
    { cars: [] },
];

_.go(
    L.range(Infinity),
    L.map(i => track[i]),
    L.map(({ cars }) => cars),
    L.map(_.delay(2000)),
    L.takeUntil(({ length: l }) => l < 4),
    L.flat,
    L.map(car => `${car} 고고!`),
    _.each(console.log)
);
