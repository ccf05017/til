# CSS Flex & Grid
## 1. Flex
### CSS 기본사항
- display: block이 기본값이다
  - 옆에 다른 요소가 오는 걸 절대 허용하지 않는다.
- display: inline-block
  - 블록 속성을 유지하면서 가로로 정렬이 가능하다.
  - inline과는 다르다
  - inline은 그냥 element이기 때문에 높낮이가 없다.

### Flexbox
#### 부모가 제어하기
- display: flex
  - 이게 다임.
- 자식을 절대 고려하지 않는다.
- flexbox container를 만들어야 된다는 소리다.
  - 굳이 명시적으로 만들 필요는 없다.
  - 그냥 그 위의 직계 부모가 container의 역할을 수행하면 된다.
- direction: row 인 경우
  - main-axis: flex-direction의 가로축을 의미한다.
    - justify-content를 통해 flex children의 가로 위치를 바꿔준다.
  - cross-axis: flex-direction의 세로축을 의미한다.
    - align-items를 통해 flex children의 세로 위치를 바꿔준다.
- direction: column 인 경우
  - row와 완전 반대로 움직인다.
- main axis는 flex-direction의 방향과 일치한다.

#### 자식이 제어하기
- align-self: 자식이 독자적으로 cross axis를 움직일 수 있다.
- order: 순서를 바꿀 수 있다.
  - order 값이 클수록 뒤로 간다.
  - 기본적으로 0이다.
- flex-basis: flex child element에게 처음 크기를 부여한다.
  - width와 비슷하지만 width의 역할만 하지는 않는다.

#### 크기 조절
- flexbox는 기본적으로 가로줄에 모든 요소를 꾸겨넣는다.
  - 내가 width, height 지정해도 무시하고 싸그리 쑤셔 넣는다.
- 이를 제어하기 위한 것이 flex-wrap
  - nowrap: 기본값. 무조건 한줄로 유지시킨다
  - wrap: 자식에 설정된 크기를 보장한다.
- align-content: flexbox 사이의 line에 대한 설정
  - align-items랑 헷갈리지 말자

## 2. Grid
### Gridbox
- flex는 가로로 놓는 데 아주 쾌적하지만, 격자 무늬 만들기는 아주 빡친다.
- 가운데 뻥 뚫린 공간이 자꾸 생김.
- display: grid 으로 시작

### column 조절
- grid-template-columns
  - 원하는 단위로 grid에 놓을만큼씩 적는다.
  - ex) grid-template-columns: 5em 10em 20em 5em;

### repeat 함수
- repeat({반복횟수}, size)
  - gird-template 에서 grid를 배열할 때 사용한다.

### row 조절
- grid-template-rows
- 나머지는 column과 같다

### 요소간 간격
- column-gap, row-gap, gap으로 관리한다.

### 템플릿 구현
- grid-template-areas

### auto-fit vs. auto-fill
- auto-fit: 고정된 공간에 늘려서 맞추기
- auto-fill: 유동적인 내용을 고정 공간에 채우기
