# 화면은 어떻게 그리나?
- 그놈의 XML로 그린다....

## LinearLayout (보충 필요)
- 선형으로 요소들을 정렬해준다.
- 주요 속성들
    - 직접 사용
        - orientation: 자식 요소들의 배열 방향을 지정한다(vertical, horizontal)
    - 자식 사용
        - layout_width="match-parent": 폭을 부모와 같게 맞춰준다.
        - layout_width="wrap_content": 폭을 컨텐츠에 맞춰준다.
        - layout-weight: 해당 요소의 비율을 지정한다. 형제 요소들간의 크기를 맞춰준다.

## RelativeLayout
- 기준점에 따라 상대적으로 요소들을 정렬해준다.
- 계산을 통해 상대적으로 배치하기 때문에 초기 로딩에서 자원 소모가 다른 레이아웃에 비해 심한 편이다.
- 물론 요즘 핸드폰 성능이 워낙 좋아서 체감이 될 정도로 심한 경우는 잘 없다 :)
- 주요 속성들
    - 자식 사용
        - layout_alignParent(left, right, top, ...): 부모 요소를 기준으로 여러 방향으로 이동
        - layout_centerInParent: 부모 기준으로 정중앙으로 이동
        - layout_centerHorizontal: 부모 기준으로 수평 중앙
        - layout_centerVertical: 부모 기준으로 수직 중앙
        - layout_to(Right, Left, ...)Of="@id/${id명}": id 전달 된 요소 기준으로 정렬

#### 참고사항
- viewComponent에 id를 부여할 수 있다: id="@+id/view2"

## Padding, Margin
- CSS와 거의 유사하다.
- margin: 주변 요소와의 여백을 나타낸다.
- padding: 해당 요소 내부의 컨텐트와 경계선 사이의 여백을 나타낸다

## FrameLayout
- 자식 요소끼리 겹치도록 만들 수 있다.
- 가장 첫번째 자식 요소가 가장 아래쪽에 배치된다.
- 겹칠 수 있는 유일한 레이아웃은 아니다(RelativeLayout도 겹쳐서 표현할 수 있다.)
- 단, FrameLayout은 자식 요소를 겹치는 것 자체가 목적이기 때문에 겹칠 때 FrameLayout을 권장한다.
