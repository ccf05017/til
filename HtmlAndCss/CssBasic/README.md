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

## 3. 선택자(Selector)
### 3.1 기본 선택자
1.  전체 선택자
    - `*` 로 사용한다 
    - 요소 내부의 모든 요소를 선택한다
2. 태그 선택자
    - `E(태그 이름)` 으로 사용한다
    - 지정된 태그 이름인 모든 요소 선택
    ```css
    /* 예시 */
   li {
       color: red;
    }   
    ```
3. 클래스 선택자
    - `.{클래스명}`으로 사용한다.
    - 지정된 클래스명과 같은 모든 요소를 선택
4. ID 선택자
    - `#{ID명}`으로 사용한다.
    - 지정된 ID명과 같은 모든 요소를 선택

### 3.2 복합 선택자
1. 일치 선택자
    - 두 개의 조건을 동시에 만족하는 요소를 선택
    ```css
   /* class명이 hello인 div 태그를 선택 */
   div.hello{
       color: red;
    }   
    ```
2. 자식 선택자
    - `부모 > 자식` 방식으로 사용
    - 부모의 자식요소를 선택
3. 후손(후위) 선택
    - `선행요소 후속요소` 방식으로 사용
    - 공백이 기호인 점에 유의할 것
    - 자식 선택자와 다르게 직계 자손 외의 지정된 선행요소 안의 모든 후속요소 조건을 찾아낸다
4. 인접 형제 선택자
    - `선행요소 + 형제요소` 방식으로 사용
    - 하나만 선택한다. 두번째 형재들부터는 찾지 않는다.
5. 일반 형제 선택자
    - `선행요소 ~ 형제요소` 방식으로 사용
    - 선행요소의 지정된 모든 형제 요소를 찾는다.

### 3.3 가상 클래스 선택자
- 선택자 앞에 `:`가 1개 붙는다. (유의사항: `:`가 두개 붙으면 가상 요소 선택자)

#### a. hover
- 요소에 마우스가 올라가 있는 동안만 해당 요소를 선택한다.
- `요소:hover` 방식으로 사용한다.
- 예시
    ```css
    /* a tag에 마우스가 올라간 동안만 빨간색으로 바꾼다 */
    a:hover {
        color: red
    }
    ```

#### b. active
- 요소를 마우스로 클릭하는 동안만 해당 요소를 선택한다.
- `요소:active` 방식으로 사용한다.
- 예시
    ```css
    /* a 태그를 클릭한 동안만 색상을 빨간색으로 변경 */
    a:active{
        color:red;
    }
    ```
  
#### c. focus
- 요소가 포커스 된 동안에만 선택 가능
- `요소:focus` 방식으로 사용한다.
- `대화형 컨텐츠`에서만 사용가능하다.
    - 대화형 컨텐츠: input, img, tabindex
- 예시
    ```css
    input {
      width: 100px;
      outline: none;
      border: 1px solid lightgray;
      padding: 5px 10px;
    }
    
    input:focus {
      background: red;
    }
    ```
  
#### d. first child
- 요소가 형제 요소 중 첫번째 요소라면 선택한다
- `요소:first-child` 방식으로 사용한다.
- 예시
    ```css
    .fuits li:first-child {
        color: red;
    }
    ```
    ```html
    <ul class="fruits">
        <li>딸기</li> <!-- 선택 -->
        <li>사과</li>
        <li>망고</li>
    </ul>
    ```
  
#### e. last child
- first child와 유사하게 동장하나, 형제 중 마지막 요소를 선택한다.
- `요소:last-child` 방식으로 사용한다.
- 예시
    ```css
    .fuits li:last-child {
        color: red;
    }
    ```
    ```html
    <ul class="fruits">
        <li>딸기</li> 
        <li>사과</li>
        <li>망고</li> <!-- 선택 -->
    </ul>
    ```
  
#### f. nth child
- 형제 중 선택된 n번째 요소를 선택한다.
- index는 0부터 시작된다.
- `요소:nth-child(n)` 방식으로 사용한다.
- 단 n 개념을 사용 시 n은 0부터 시작한다.
- 유의사항
    - nth child가 우선적으로 검색된 뒤 앞의 요소를 확인한다.
    - 오른쪽부터 읽으란 얘기다. 
- 예시(일반 사용)
    ```css
    .fuits li:nth-child(2) {
        color: red;
    }
    ```
    ```html
    <ul class="fruits">
        <li>딸기</li> 
        <li>사과</li> <!-- 선택 -->
        <li>망고</li>
    </ul>
    ```
- 예시(n키워드 사용)
    ```css
    .fuits li:nth-child(n+2) {
        color: red;
    }
    ```
    ```html
    <ul class="fruits">
        <li>딸기</li> 
        <li>사과</li> <!-- 선택 -->
        <li>망고</li> <!-- 선택 -->
    </ul>
    ```
  
#### g. nth of type
- 요소의 타입과 동일한 타입인 형제 요소 중 요소가 n번째 요소라면 선택
- 표현부터가 어렵다
- `요소:nth-of-type(n)` 방식으로 사용한다
- nth child 처럼 n 요소를 사용할 수 있다.
- 타입(태그 이름)에만 유효하다. (클래스 이름 같은 거엔 반응하지 않는다)
- 예시
    ```css
    .fruits p:nth-of-type(1) {
      color: red;
    }
    ```
    ```html
    <div class="fruits">
      <div>딸기</div>
      <p>사과</p> <!-- 선택됨 -->
      <p>망고</p>
      <span>오렌지</span>
    </div>
    ```

#### h. 부정 선택자
- 선택자가 아닌 요소를 선택한다
- `요소:not(선택자)` 방식으로 사용한다.
- 예시
    ```css
    .fruits li:not(.except) {
      color: red;
    }
    ```
    ```html
    <ul class="fruits">
      <li>딸기</li>
      <li class="except">사과</li> <!-- 제외됨 -->
      <li>망고</li>
      <li>오렌지</li>
    </ul>
    ```
