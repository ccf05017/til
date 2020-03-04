# CSS 학습 정리
## 1. 기본 문법
```css
선택자 {
    속성명: 속성값;
    ...
}
```

## 2. 선언 방식
### 2.1 인라인(inline) 방식
- html 안에 직접 스타일을 넣는 방식
```html
<div style="속성명: 속성값;"></div>
```
- 관리의 어려움 때문에 직접적으로 내가 만들 때 사용하는 걸 권장하진 않는다.
- 기타 라이브러리에서 삽입되는 경우 '아 그렇구나' 이해할 정도로 학습하자

### 2.2 내장(embedded) 방식
- html style 태그 안에 작성하는 방식
- 보통 head 태그의 children으로 넣는다
```html
<head>
    <style>
        선택자 {
            속성명: 속성값;
            ...
        } 
    </style>
</head>
```

### 2.3 링크(link) 방식
- 외부의 CSS 파일을 가져오는 방식
- 가장 많이 사용하는 방식
- link를 통해 여러개의 CSS를 부르면 병렬로 처리된다.
```html
<head>
    <link rel="stylesheet" href="${CSS 파일경로}">
</head>
```

### 2.4 @import 방식
- 외부 문서를 CSS로 가져오는 방식
- link와 반대로 직렬로 처리된다.
```html
<head>
    <link rel="stylesheet" href="${CSS 파일경로}">
</head>
```
```css
/* CSS 파일1 - html로 삽입되는 파일 */
@import url("${불러올 CSS 파일 경로}");
```
```css
/* CSS 파일2 - 실제 내용이 있는 파일 */
선택자 {
    속성명: 속성값;
    ...
}
```
