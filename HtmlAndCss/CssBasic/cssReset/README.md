# CSS 초기화
## 왜 해야하는가?
- 브라우저마다 기본 설정이 있고, 이 기본설정을 그대로 사용하면 브라우저마다 화면이 다르게 보인다.
- 예를 들어 body 태그 내에 아무 속성 정의 없이 아래 CSS를 적용하면 크롬 브라우저에서 여백이 발생한다.
```css
.box {
    width: 300px;
    height: 150px;
    background-color: tomato;
}
```

- 이를 방지하기 위해 body 태그의 마진을 없애준다(css 초기화)
```css
body {
    margin: 0
}
```

## 이걸 매번 해야하는가?
- 귀찮기 때문에 매번 이렇게 하지는 않는다.
- reset.css 사용하는 경우가 많다.
- 관련 링크: https://www.jsdelivr.com/package/npm/reset-css
- 유의사항
    - link 형태로 CSS-reset 사용 시 반드시 가장 먼저 실행되도록 위치를 최상단에 배치한다.
