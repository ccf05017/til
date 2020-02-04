// 1000ms 뒤에 실행된다는 상황 자체를 명시적으로 표현함
const delay1000 = a => new Promise(resolve => 
    setTimeout(() => resolve(a), 1000));

const go1 = (a, f) => f(a);
const add5 = a => a + 5;

const result = go1(10, add5);
console.log(result);

const go2 = (a, f) => a instanceof Promise ? a.then(f) : f(a);

// 1000ms 뒤에 실행되는 상황을 add5 함수에 적용한다는 것을 명시적으로 표현
const result2 = go2(delay1000(10), add5)
result2.then(console.log);
