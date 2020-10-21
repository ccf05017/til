const MyReact = (function() {
  // 모듈 스코프 안에 state를 설정하고, 아래서 매번 재할당 한다.
  let _val;

  return {
    render(Component) {
      const Comp = Component();
      Comp.render();
      return Comp;
    },
    useState(initialValue) {
      _val = _val || initialValue;  // <- 매번 '재할당'이 일어난다. 이게 핵심!
      function setState(newVal) {
        _val = newVal;
      }
      return [_val, setState];
    }
  }
})();

// Hook을 통해 Counter 함수 컴포넌트를 선언적으로 사용할 수 있다.
// 카운터 함수는 사이드 이펙트로부터 자유롭다.
function Counter() {
  const [count, setCount] = MyReact.useState(0);

  return {
    click: () => setCount(count + 1),
    render: () => console.log(`render: ${count}`),
  }
}

let App;
App = MyReact.render(Counter);
App.click();
App = MyReact.render(Counter);
