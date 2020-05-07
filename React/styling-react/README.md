# 리액트에서 스타일링하기
## Sass
- CSS 스타일 가이드
- 자세한 건 [여기](https://sass-guidelin.es/ko/)
- 최근에는 `.scss` 확장자를 사용함. (과거에는 `.sass` 사용) 
- flex 잘 모를땐 `flex frog` 한번 보고 오자
- 클래스를 통한 컴포넌트 스타일링 팁
    ```javascript
    return <button className={['Button', size].join(' ')}>{children}</button>
    ```
- 위와 같은 상황에서 좋은 서드 파티 라이브러리 => classnames
    - 조건부 스타일링을 도와준다.
- 특정 css 요소끼리 겹쳤을 때
    ```scss
    .class + .class {
      // 이런 식으로 작성하면 두 클래스 요소가 만났을 때만 아래 속성이 적용된다.
      font-size: 1rem;
    }
    ```
