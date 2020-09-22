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
  
## CSS Module
- 서드파티 라이브러리
- 이를 통해서 사용하면 CSS가 절대 겹치지 않도록 해준다.
    - 작성한 CSS를 별도의 객체를 만들어서 감싸줌
- SCSS도 지원한다.
- CSS 네이밍 정책 정하기 귀찮거나 `레거시랑 연동할 때` 쓰면 아주 좋다.
- 사용법
    - CSS 생성은 기존과 똑같이
    - 불러올 때만 좀 다르다
        ```javascript
        import styles from "./{fileName}.module.css"
        ```

## React Icons
- 리액트에서 사용 가능한 아이콘들을 모아 놓은 모듈
- 공식문서에서 사용할 요소를 검색하고 해당 이름으로 import 하면 됨 [링크](https://react-icons.github.io/react-icons/)
- 예시
    ```javascript
    import { IconName } from "react-icons/bs";
    ```
