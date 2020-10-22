function useState(initialState) {
  let _val = initialState;

  function state() {
    return _val;
  }

  function setState(newVal) {
    _val = newVal;
  }

  return [state, setState];
}

function Counter() {
  const [count, setCount] = useState(0);

  return {
    click: () => setCount(count() + 1),
    render: () => console.log(`render: ${count()}`),
  }
}

const C = Counter();
C.render();
C.click();
C.render();

// 하지만 리액트에서는 상태를 함수로 전달하지 않는다.
// 이건 또 어떻게 구현해야 될까?
