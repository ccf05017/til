const MyReact = (function() {
  // 모듈 스코프 안에 state를 설정하고, 아래서 매번 재할당 한다.
  let _val, _deps;

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
    },
    useEffect(callback, depArray) {
      // 배열을 활용해서 의존성 변경 여부를 파악한다.
      const hasNoDeps = !depArray;
      // array every method: 모든 구성 원소가 해당 조건에 맞는지 확인하는 내장함수
      const hasChangedDeps = _deps ? !depArray.every((element, i) => element === _deps[i]) : true
      console.log(hasNoDeps);
      console.log(hasChangedDeps);

      if (hasNoDeps || hasChangedDeps) {
        callback();
        _deps = depArray;
      }
    }
  }
})();

// Hook을 통해 Counter 함수 컴포넌트를 선언적으로 사용할 수 있다.
// 카운터 함수는 사이드 이펙트로부터 자유롭다.
function Counter() {
  const [count, setCount] = MyReact.useState(0);

  MyReact.useEffect(() => {
    console.log('effect', count);
  }, [count]);

  return {
    click: () => setCount(count + 1),
    noop: () => setCount(count),
    render: () => console.log(`render: ${count}`),
  }
}

let App;
App = MyReact.render(Counter);
App.click();
App = MyReact.render(Counter);
App.noop();
App = MyReact.render(Counter);
App.click();
App = MyReact.render(Counter);

// 하지만 여전히 문제가 있다.
// 위의 미니 리액트는 '잘못 구현된 싱글톤 오브젝트'다.
// state나 effect가 여러개가 되면 에러를 내뿜는다.
