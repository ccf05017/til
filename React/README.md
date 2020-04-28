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

### 규칙
- 태그는 반드시 쌍을 맞춰서 닫는다.
    - br, input도 예외는 없다.
    - 단 셀프 클로징 태그는 가능하다.(ex. `<br/>`)
- 두 개 이상의 태그는 반드시 하나의 태그로 감싸져 있어야 한다.
    - Fragment로 감싸서 처리도 가능하다.(빈 태그)
- JSX 안의 자바스크립트 값은 중괄호를 사용한다.
    - `<div>{name}</div>`
- inline 스타일링: 스타일 객체를 만들어서 전달해준다. + 대시 구분은 카멜 케이스로 전환한다.
    ```jsx harmony
    const style = {
        backgroundColor: 'black',
        color: 'aqua',
        fontSize: 24,
        padding: '1rem'
    };
    
    <div style={style}></div>
    ```
- 클래스 지정: className 속성으로 클래스 지정 가능 (class라고 해도 작동은 하지만 경고가 주렁주렁 달릴 것)
- 주석 처리: 이것도 주석으로 감싸서 html 주석처럼 작성한다.
    ```jsx harmony
    {/* 주석은 이렇게 */}
    ```
