# React에서 외부 API 호출하기
## 개요
- 늘 그렇듯 비동기로 처리해야 한다.
- 보통 리액트로 외부와 연동하면 세가지 상태를 관리하게 된다
    - response data, loading, error
    
## Reducer로 요청 상태 관리하기
- state로 컴포넌트 내부에서 관리해도 된다.
- 하지만 API 요청의 경우 모든 컴포넌트에서 사용할 공용 기능일 가능성이 높다.
- 그렇기 때문에 reducer를 활용해서 해당 로직을 분리해주는 게 유지보수에 좋다.

## react-async로 요청 상태 관리하기
- 서드 파티 라이브러
- 커스텀 훅을 작성해서 직접 사용하고 싶다면 이전 방법으로 사용해도 된다.
- 매번 작성하기가 귀찮다면 이 라이브러리를 이용해도 무관.

## Context에서 비동기로 API 상태 관리하기
- 로그인 토큰 같은 거 관리할 때 사용하면 좋다
- 이 상황에서는 State Context와 Dispatch Context를 분리해서 구현하는 게 좋다.
- 코드가 엄청 꼬였는데 어떻게 해결해야 할 지 감이 잘 안 옴...
    - 향후 수정하자 일단 패스
