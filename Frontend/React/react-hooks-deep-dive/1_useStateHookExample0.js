function useState(initialState) {
  let _val = initialState;

  // state 함수가 클로저(내부함수이기도 함)
  function state() {
    return _val;
  }

  // setState도 마찬가지
  function setState(newVal) {
    // 여기서 중요한건 _val을 외부에 노출하지 않고 상태를 변경했다는 점
    _val = newVal;
  }

  return [state, setState];
}

const [foo, setFoo] = useState(0);

console.log(foo());
setFoo(1);
console.log(foo());
