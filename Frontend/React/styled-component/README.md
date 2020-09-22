# Styled Components
## 개요
- 자바스크립트에서 CSS를 정의할 수 있게 만드는 라이브러리
- 이것도 서드 파티 라이브러리다
- 현재 제일 유행중일 뿐 표준화된 무언가는 아님
- `Tagged Template Literal` 을 활용함

## Tagged Template Literal
- Template Literal?
    - 문자열 안에 자바스크립트를 넣는 기법 
- 함수 뒤에 바로 Template Literal을 넣으면 Tagged Template Literal이라 부른다.
- 예시
    ```javascript
    const red = 'red';
    const blue = 'blue';
    
    function favoriteColors(texts, ...values) {
        console.log(texts);
        console.log(values);
    }
    
    favoriteColors`제가 좋아하는 색은 ${red}과 ${blue}입니다.`;
    
    // ["제가 좋아하는 색은 , "과 ", "입니다.", raw: Array(3)]
    // ["red", "blue"]
    ```
- 이를 이용하면 HTML 태그를 Template Literal로 뽑아낼 수 있다.
- 예시2
    ```javascript
    const red = 'red';
    const blue = 'blue';
    
    function favoriteColors(texts, ...values) {
        return texts.reduce((result, text, i) => 
            `${result}${text}${values[i] ? `<b>${values[i]}</b>` : ''}`, '');
    }
    
    favoriteColors`제가 좋아하는 색은 ${red}과 ${blue}입니다.`;
    
    // favoriteColors`제가 좋아하는 색은 ${red}과 ${blue}입니다.`
    ```
- FxJS에서 template 함수 정의해서 화면 그리던 예제와 유사한 방식
    - 간단하기로는 FxJS가 훨씬 간단하긴 하다.
    
## Polished
- 유용한 함수들이 들어있는 서드파티 라이브러리 [링크](https://polished.js.org/)

## Transition
- CSS @keyframes를 활용함 [링크](https://developer.mozilla.org/en-US/docs/Web/CSS/@keyframes) 
