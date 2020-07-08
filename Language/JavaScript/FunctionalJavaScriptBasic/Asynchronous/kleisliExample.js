const users = [
    { id: 1, name: 'aa'},
    { id: 2, name: 'bb'},
    { id: 3, name: 'cc'},
]

const getUserById = id => 
    users.find(u => u.id == id) || Promise.reject("empty!");

const f = ({name}) => name;
const g = getUserById;

const safeFg = id => Promise.resolve(id).then(g).then(f).catch(a => a);

safeFg(2).then(console.log);        // 이 부분은 원래 실행 가능한데 아래와 똑같이 에러가 발생한다.(불변성 보장)

// 안전하지 못한 상황을 강제로 만들어낸다.
users.pop();
users.pop();

safeFg(2).then(console.log);        // f가 실행되지 않고 g의 에러만 나온다.
