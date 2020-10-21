function useState(initialState) {
  let _val = initialState;

  // state 함수 없이 수정자만 내보내고 내부값을 그대로 노출시킨다.
  function setState(newVal) {
    _val = newVal;
  }

  return [_val, setState];
}

// 여기서 이미 useState 내부 상태값의 할당이 끝났다.
// setFoo를 오천만번 해도 할당이 끝났기 때문에 내부 상태는 영원히 바뀌지 않는다.
const [foo, setFoo] = useState(0);

console.log(foo);
setFoo(1);
console.log(foo);
// 영원히 0, 0이 출력될 것이다.
