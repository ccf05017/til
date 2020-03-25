# 화면은 어떻게 그리나?
- 그놈의 XML로 그린다....

## LinearLayout
- 요소들의 정렬을 지정해준다.
- 주요 속성들
    - 직접 사용
        - orientation: 자식 요소들의 배열 방향을 지정한다(vertical, horizontal)
    - 자식 사용
        - layout_width="match-parent": 폭을 부모와 같게 맞춰준다.
        - layout_width="wrap_content": 폭을 컨텐츠에 맞춰준다.
        - layout-weight: 해당 요소의 비율을 지정한다. 형제 요소들간의 크기를 맞춰준다.