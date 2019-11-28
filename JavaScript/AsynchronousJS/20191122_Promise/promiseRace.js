const car1 = new Promise(resolve => setTimeout(resolve, 2000, "car1"));
const car2 = new Promise(resolve => setTimeout(resolve, 1000, "car2"));
const car3 = new Promise(resolve => setTimeout(resolve, 3000, "car3"));

Promise.race([car1, car2, car3]).then(value => {
    console.log(value);
});