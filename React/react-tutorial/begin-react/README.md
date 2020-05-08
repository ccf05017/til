# React 학습 정리
- 업데이트는 신경 끄자. `어떻게 보일지`에 집중

## 시작
- 모든 컴포넌트의 시작은 `import React from 'react'`
- 함수형으로 만들자
- index.html 안의 root 태그에 index.js가 App.js를 넣으면서 리액트는 시작된다.

## JSX
- html 같이 생긴 JS
- 역시나 Babel이 관여한다. [Babel 링크](https://babeljs.io)
- 내부적으로 `React.createElement()` 함수로 컴포넌트를 만들어준다.

### JSX 작성 규칙
#### 1. 태그는 반드시 쌍을 맞춰서 닫는다.
- br, input도 예외는 없다.
- 단 셀프 클로징 태그는 가능하다.(ex. `<br/>`)
#### 2. 두 개 이상의 태그는 반드시 하나의 태그로 감싸져 있어야 한다.
- Fragment로 감싸서 처리도 가능하다.(빈 태그)
#### 3. JSX 안의 자바스크립트 값은 중괄호를 사용한다.
- `<div>{name}</div>`
#### 4. inline 스타일링 
- 스타일 객체를 만들어서 전달해준다. (대시 구분`-`은 카멜 케이스로 전환한다.)
    ```jsx harmony
    const style = {
        backgroundColor: 'black',
        color: 'aqua',
        fontSize: 24,
        padding: '1rem'
    };
    
    <div style={style}></div>
    ```
#### 5. 클래스 지정
- className 속성으로 클래스 지정 가능 (class라고 해도 작동은 하지만 경고가 주렁주렁 달릴 것)
#### 6. 주석 처리 
- 이것도 중괄호로 감싸서 html 주석처럼 작성한다.
    ```jsx harmony
    {/* 주석은 이렇게 */}
    ```

## Props
- 상위 컴포넌트에서 전달되는 값
- 함수의 인자로 전달된다.
- children 속성
    - 해당 컴포넌트 태그의 사이에 있는 리액트 컴포넌트들을 가리킨다.
```javascript
function App() {
  return (
      <Wrapper>
        <Hello name="react" color="red" />
        <Hello color="pink" />
      </Wrapper>
  );
}

function Wrapper({ children }) {
    // children에 위의 Wrapper 사이에 있는 컴포넌트들이 전달된다.
    const style = {
        border: '2px solid black',
        padding: 16
    };

    return <div style={style}>{children}</div>
}
```

## 조건부 렌더링
- 조건에 따라 렌더링 여부를 결정하는 코드
- 가장 대표적인 구현법은 3항 연산자
    ```javascript
    function Hello({ color, name, isSpecial }) {
        // ...
                {isSpecial ? <b>*</b> : null}
                Hello {name}!
            </div>
        // ...
    }
    ```
- 3항 연산자의 경우 조건에 따라 값이 바뀔 때 사용하는 게 좋다.
- 팁: null, undefined를 렌더링하면 아무 내용도 나오지 않는다.
- 조건이 간단할 때는 and 연산자를 사용하는 방법도 있다.
    ```javascript
    function Hello({ color, name, isSpecial }) {
        // ...
                {isSpecial && <b>*</b>}
                Hello {name}!
            </div>
        // ...
    }
    ```
- and 연산자는 조건에 따라 값을 숨길 때 사용하는 게 좋다.

## useState를 통한 동적 상태 관리
- react 훅 중 한가지
- 이벤트 핸들러 달아주기
    - 컴포넌트의 on{이벤트명} 속성을 통해 이벤트 핸들러를 달아줄 수 있다.
    - 이벤트 핸들러 넘겨줄 때 넘기는 건 `함수 타입`이다.
- `[{관리하고 싶은 요소 이름}, set{관리하고 싶은 요소 이름}] = useState({초기값})` 형태로 사용 가능
    ```javascript
    const [number, setNumber] = useState(0);
    ```
- 또한 세터 함수의 인자로 업데이트 함수를 전달할 수 있다.

## 리액트에서의 input 값 관리
- useState 함수를 통해 input 값 변화를 관리한다.
- input의 value 속성에 관리할 값을 꼭 넣어주자
    - 이거 빼먹으면 UI 상에서 input 창 값 변화를 주기가 어렵다.
- 단일 input 관리 예제
    ```javascript
    const [text, setText] = useState('');
    
    return (
        <div>
            <input onChange={onChange} value={text}/>
            <button onClick={onReset}>초기화</button>
            <div>
                <b>값: </b>
                {text}
            </div>
        </div>
    );
    ```
- 여러개의 input을 관리할 때는 객체 형태로 한꺼번에 관리한다.
    - 무식하게 핸들러 여러개 달고 상태 여러개 만들어서 처리하지 말아라.
- `훅을 통해서 객체를 수정할 때는` 객체 사본을 변경해서 적용해야 한다.(불변 유지)
- 예제
    ```javascript
    const [inputs, setInputs] = useState({
        name: '',
        nickname: '',
    });
    const { name, nickname } = inputs;
    
    const onChange = (e) => {
        const { name, value } = e.target;
    
        setInputs({
           ...inputs,
           [name]: value,
        });
    };
    
    return (
        <div>
            <input name="name" placeholder="이름" onChange={onChange} value={name} ref={nameInput}/>
            <input name="nickname" placeholder="닉네임" onChange={onChange} value={nickname}/>
            <button onClick={onReset}>초기화</button>
            <div>
                <b>값: </b>
                {name} ({nickname})
            </div>
        </div>
    );
    ```

## useRef로 특정 DOM 선택하기
- 일반 JS에서는 DomSelector 함수를 사용했음
- 비디오, 그래프, 스크롤 위치 추적 등등을 위해 필요함
- 리액트에서는 이런 상황에서 ref를 사용한다.
    - 함수형: useRef
    - 클래스형: React.createRef() 혹은 콜백 함수
- 렌더링과 관계 없는 변수를 관리할 때 사용되기도 한다.
- 사용 예시
```javascript
const nameInput = useRef(); // ref 정의. 객체 형태로 생성된다.

const onReset = () => {
    setInputs({
        name: '',
        nickname: '',
    });
    // {ref}.{current}.{DOM 메서드}
    nameInput.current.focus();
};

return (
    <div>
        {/* ref 속성에 html class 처럼 박아 넣는다 */}
        <input name="name" placeholder="이름" onChange={onChange} value={name} ref={nameInput}/>
    </div>
);
```

## 배열 렌더링하기
- 보통 리스트 컴포넌트와 안에 들어갈 내용 컴포넌트를 따로 만든다.
- 이때 컴포넌트는 한 파일에 작성해도 되고, 다른 파일로 나눠도 된다.
- 자바스크립트 내장 map으로 처리하면 편하다
    ```javascript
    return (
        <div>
            {
                users.map(
                    user => (<User user={user} key={user.id} />)
                )
            }
        </div>
    );
    ```
- 배열을 사용할 때는 반드시 배열의 각 요소에 반드시 `key` prop이 필요하다.
    - 이게 있어야 virtual DOM에서 어떤 대상을 렌더링해야 할 지 정확하게 파악 가능하다.
    - 이를 사용하면 성능 최적화에도 영향을 미친다. 
- FxJS와의 통합도 가능하다. (이미 함수형 지원되는데 또 이걸 쓰는 게 의미가 있을지는 생각해보고 쓰자)
    ```js
    return (
        <div>
            {
                _.go(
                    users,
                    L.map(user => (<User user={user} key={user.id} />)),
                    C.takeAll
                )
            }
        </div>
    );
    ```

## useRef로 컴포넌트 안의 변수 만들기
- 값을 바꿔도 재렌더링되지 않았으면 좋겠는 값을 다룰 때 사용하면 좋다.
- setTimeout, setInterval, 서드파티 라이버르리를 사용하기 위해 사용된 인스턴스 등을 관리할 때 사용
- 중요한 건 useRef 값은 변경되도 재렌더링되지 않는다는 것!

## 배열에 항목 추가하기
- 배열도 useState를 사용해서 관리한다.
- 일반 객체와 마찬가지로 불변 유지를 위해 기존 배열을 복사해서 추가한 뒤 이 값을 useState에 인자로 전달한다.
- 보통 전개 연산자(spread)나 concat을 활용한다.
- push, splice 이딴 거 사용하지 말아라.
    - 반드시 꼭 해야겠다면 복사해서 사용하고 새로 만들어서 던져라

## 배열의 항목 제거하기
- 보통 삭제 핸들러에 인자로 삭제 대상을 전달해야 할 것이다.
    - JSX에서 함수를 바로 실행하는 건 불가능하다.
    - 콜백 함수로 감싸서 안에 내가 원하는 함수 실행을 넣고 전달하자.
- JS 내장 filter 함수를 사용하면 좋다.

## 배열의 항목 수정하기
- 여기서도 map 쓰면 된다.
- 아무튼 값을 바꿔야 할 일이 생긴다면 map을 찾자
- 3항 연산자를 통해 구현하는 방법도 있다.
- 물론 filter와 조합해서 만들어도 될 것

## useEffect Hook
- 컴포넌트의 생명주기 관련 훅을 추가할 수 있다.
- 사용방법 `useEffect({실행할 함수 콜백}, {dependencies array})`
- `url 변화에 따라 페이지 내용을 바꿔야할 때` 아주 유용하다.
- dependency array가 비었을 때
    - 마운트 함수 예시
        ```javascript
        useEffect(_ => {
            console.log("두둥등장!");
        }, []);
        ```
        - props를 state로 설정할 때, REST API 콜, 외부 라이브러리 사용 등에 활용
    - 언마운트 함수 예시
        ```javascript
        useEffect(_ => {
            console.log("두둥등장!");
            return _ => {
                console.log("두둥사라짐!");
            }
        }, []);
        ```
        - clear 인터벌, clear 타임아웃, 라이브러리 인스턴스 제거 등에 활용
 - dependency array에 값이 있을 때
    - 값의 변화를 추적할 수 있게 된다.
        ```javascript
        useEffect(_ => {
            console.log('user 값이 설정됨:');
            console.log(user);
            return _ => {
                console.log('user 값이 바뀌기 전:');
                console.log(user);
            }
        }, [user]);
        ```
- 주의 사항: state, props를 useEffect에서 사용할 때는 꼭 dependency array에 넣어주자.

## useXXX Hook을 이용한 성능 최적화
- 가장 중요한 건 아래 내용들 모두 확실하게 성능 최적화를 할 수 있는 포인트가 보일 때만 사용해라
- 늘 그렇듯 어설픈 성능 최적화는 오히려 성능 저하를 발생시킨다. (웬만하면 원작자가 나보다 똑똑하다.)

### useMemo Hook
- 이전에 계산한 값을 재활용할 때 사용
- 성능 최적화에 사용한다는 이야기
- 특정값이 바뀌었을 때만 특정 함수를 실행하도록 해준다.
- 예시
    ```javascript
    const count = useMemo(_ => countActiveUsers(users), [users]);
    
    return (
        <>
            <CreateUser username={username} email={email} onChange={onChange} onCreate={onCreate}/>
            <UserList users={users} onRemove={onRemove} onToggle={onToggle}/>
            <div>활성 사용자 수: {count}</div>
        </>
    );
    ```

### useCallback Hook
- useMemo의 함수용 버전
- 기존 방식은 컴포넌트가 리렌더링 될 때마다 함수를 다시 만든다.
- 성능상 큰 문제가 발생하진 않지만, 향후 props 변화가 없으면 리렌더링조차 하지 않게 개선할 때는 문제가 될 수 있다.
- 예시
    ```javascript
    const onChange = useCallback(e => {
        const {name, value} = e.target;
        setInputs({
            ...inputs,
            [name]: value
        })
    }, [inputs]);
    ```
 - 위 예시의 경우 inputs가 변경될 때만 해당 함수를 다시 만든다. 그 외에는 기존 함수를 그대로 사용함.
 - 모든 Hook과 마찬가지로 해당 함수가 상태를 참조한다면 꼭 deps 배열에 추가해주자.

### React.memo
- 컴포넌트 리렌더링이 불필요한 상황에 리렌더링을 하지 않도록 만들어주는 함수
- 적용 방법
    - 우선 사용하는 컴포넌트들을 React.memo 함수로 감싸준다.
    - state 중에 기존값을 참조하는 대상들을 골라낸다.
    - 해당 대상들을 함수형으로 업데이트하도록 변경한다.

## useReducer
- 상태를 업데이트하는 또다른 방법
- setState와의 차이점
    - action 객체를 통해 상태를 업데이트 함
    - 업데이트에 필요한 참조값을 추가할 수 있음
- 상태 업데이트 로직을 컴포넌트 밖으로 분리 가능함
- 동작 자체를 추상화하고 다른 곳에서 한꺼번에 관리할 수 있다.
- 어떤 훅을 사용하건 상태의 불변성 유지에 각별히 신경쓸 것
- useState vs. useReducer?
    - 늘 그렇듯 정해진 규칙은 없다.
    - 상황에 맞게 트레이드오프 해라
    - 권장안은 useState를 먼저 사용하다가 불편해질 거 같을 때 useReducer로 변경

## Custom Hook
- 반복되는 공용 함수들이 있다.
- 이 때를 위한 Hook
- input 상태를 관리하는 Hook 예시
    ```javascript
    function useInputs(initialForm) {
        const [form, setForm] = useState(initialForm);
    
        const onChange = useCallback(e => {
            const { name, value } = e.target;
            setForm(form => ({ ...form, [name]: value }));
        }, []);
    
        const reset = useCallback(() => setForm(initialForm), [initialForm]);
    
        return [form, onChange, reset];
    }
    ```

## Context API
- 전역값 관리
- 하지만 아샬님 얘기대로 아주아주 주의해서 사용하자
    - 값 관리 자체가 목적이라면 가능한 state, props에서 끊어내자
- 함부로 사용하면 관리가 어렵다.

## Immer
- 서드파티 라이브러리
- 불변성을 좀 더 쉽게 지킬 수 있도록 도와줌
- 이거 대신 FxJS를 쓰는 것도 고려하면 좋을듯
- 내부적으로 ES6의 프록시 기능을 사용함
    - 리액트 네이티브 같은 환경에서는 지원되지 않기 때문에 ES5로 변환됨(성능 저하가 있음)
- 진짜 복잡할 때 사용하는 걸 권장
    - 간단한 건 그냥 기본 JS reducer를 사용하자
