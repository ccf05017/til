const MyReact = (function() {
  let hooks = [], currentHook = 0;

  return {
    render(Component) {
      const Comp = Component();
      Comp.render();
      currentHook = 0;
      return Comp;
    },
    useEffect(callback, depArray) {
      const hasNoDeps = !depArray;
      const deps = hooks[currentHook];
      const hasChangedDeps = deps ? !depArray.every((el, i) => el === deps[i]) : true;

      if (hasNoDeps || hasChangedDeps) {
        callback();
        hooks[currentHook] = depArray;
      }

      currentHook++;
    },
    useState(initialValue) {
      hooks[currentHook] = hooks[currentHook] || initialValue;
      const setStateHookIndex = currentHook; // 아래의 setState 클로저를 위해
      const setState = (newState) => (hooks[setStateHookIndex] = newState);
      return [hooks[currentHook++], setState];
    },
  }
})();

// 예제 4로 부터 이어짐 - 사용하는 곳
function Counter() {
  const [count, setCount] = MyReact.useState(0)
  const [text, setText] = MyReact.useState('foo') // 두번 째 상태 Hook!
  MyReact.useEffect(() => {
    console.log('effect', count, text)
  }, [count, text])
  return {
    click: () => setCount(count + 1),
    type: (txt) => setText(txt),
    noop: () => setCount(count),
    render: () => console.log('render', { count, text }),
  }
}
let App
App = MyReact.render(Counter)
// 이펙트 0 foo
// render {count: 0, text: 'foo'}
App.click()
App = MyReact.render(Counter)
// 이펙트 1 foo
// render {count: 1, text: 'foo'}
App.type('bar')
App = MyReact.render(Counter)
// 이펙트 1 bar
// render {count: 1, text: 'bar'}
App.noop()
App = MyReact.render(Counter)
// // 이펙트가 실행되지 않음
// render {count: 1, text: 'bar'}
App.click()
App = MyReact.render(Counter)
// 이펙트 2 bar
// render {count: 2, text: 'bar'}
