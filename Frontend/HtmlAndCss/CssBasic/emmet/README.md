# Emmet
- 쾌적한 프론트엔드 개발을 위해 익혀두면 아주아주 좋다.
- 자세한 내용은 `https://emmet.io` 참조

## 기본 문법
- css 선택자로 기본적인 div 태그에 클래스를 넣은 상태로 생성 할 수 있다.
```html
.test + (tab key) => <div class="test"></div>
```

- 좀 더 복잡한 예제
```css
.container>ul.list>li.lit-item*10>a{list$} + (tab key)
```

```html
<div class="container">
    <ul class="list">
        <li class="lit-item"><a href="">list1</a></li>
        <li class="lit-item"><a href="">list2</a></li>
        <li class="lit-item"><a href="">list3</a></li>
        <li class="lit-item"><a href="">list4</a></li>
        <li class="lit-item"><a href="">list5</a></li>
        <li class="lit-item"><a href="">list6</a></li>
        <li class="lit-item"><a href="">list7</a></li>
        <li class="lit-item"><a href="">list8</a></li>
        <li class="lit-item"><a href="">list9</a></li>
        <li class="lit-item"><a href="">list10</a></li>
    </ul>
</div>
```

- 아쉽게도 위와 같이 복잡한 예를 자주 만들 일은 없다.
- HTML 뿐 아니라 CSS도 emmet 문법 적용이 가능하다.
