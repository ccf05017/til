# React Router
## 개요
- SPA 개발 때 사용된다.
- 서버 부하 감소, 좀 더 좋은 UX 등의 장점이 있다.
- 리액트에서는 컴포넌트를 기반으로 라우팅 한다.

## 단점
- 자바스크립트 코드가 너무 비대해질 수 있다 => Code Splitting으로 해결
- 자바스크립트가 실행 불가능한 환경에서는 사용할 수 없다 -> Server Side Rendering으로 해결

## 주요 라이브러리
- React-Router
    - 컴포넌트 기반으로 라우팅을 해준다
    - 주로 기존에 있던 브라우저 탐색 기능에서 새로고침을 막아놓는 형태로 구현된다.
- Next.js
    - 파일경로, 이름을 기반으로 라우팅한다.
    - 서버 사이드 렌더링과 코드 스플리팅을 쉽게 만들어준다.

## URL 변수
- 기본 라이브러리로 처리 가능
- match props 사용

## Query 변수
- location props 사용
- qs 라이브러리가 있어야 파싱이 편하다.

## 서브 라우트
- 라우터 안에서 라우팅을 한 번 더 하는 것
- 특정 경로에 탭이 있는 경우 구현하면 편하다

## React Router 부가기능
### history 객체
- 라우터를 사용하는 컴포넌트에 props로 제공됨.
- 컴포넌트에서 라우터에 직접적으로 접근할 수 있다.
- action: 라우터에서 가장 마지막으로 발생한 action이 무엇인지 보여줌
    - PUSH: 링크를 통해 페이지를 이동하는 것
    - POP: 뒤로가기나 앞으로 가기로 페이지를 이동하는 것
- block: 사용자가 페이지 이탈하는 것을 방지
- go: 앞으로가기나 뒤로가기를 실행해주는 함수
    - goBack, goForward
- length: 방문 길이를 표현
- listen: 경로에 변경이 생겼을 때 특정 함수를 호출하고 싶을 때 사용
- location: 현재 자신이 있는 경로
- push: 다른 경로로 이동
- replace: 방문 기록을 남기지 않고 다른 경로로 이동

### withRouter
- 라우터 컴포넌트가 아닌 곳에서 match, history, location 사용
- location vs. match?
    - location은 절대 경로
    - match는 상대 경로. 현재 마운트 된 컴포넌트 위치를 기준으로 계산한다.
    
### Switch
- 여러 라우트 중 하나만을 보여줌
- 존재하지 않는 페이지(404)를 구현할 때 아주 좋다.

### NavLink
- 현재 주소와 일치한다면 스타일을 바꿔준다.
- activeStyle, activeClassName 속성으로 바꿀 스타일을 지정해줄 수 있다.

### Prompt
- 사용자 이탈 방지(block)을 컴포넌트로 만들어 줄 수 있다.

### Redirect
- 해당 컴포넌트를 렌더링하는 순간 다른 경로로 이동

### Route Config
- 라우팅 경로를 사전에 등록하는 기능

## useReactRouterHook
- 아직 공식 지원되지는 않는다.
- 공식적으로 지원되면 그 때 알아보고 쓰자
