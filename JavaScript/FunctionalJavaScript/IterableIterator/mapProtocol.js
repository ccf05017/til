const map = new Map([['a', 1], ['b', 2], ['c', 3]]);

console.log(map.values());   // value '이터레이터' 반환
console.log(map.keys());     // key '이터레이터' 반환
console.log(map.entries());  //  entries '이터레이터' 반환

console.log("Only Values--------------------")
for (const a of map.values()) console.log(a);

console.log("Only Keys----------------------")
for (const a of map.keys()) console.log(a);

console.log("Full Map-----------------------")
for (const a of map.entries()) console.log(a);
