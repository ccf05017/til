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
- 자식 요소끼리 겹치도록 만들 수 있다.자
- 가장 첫번째 자식 요소가 가장 아래쪽에 배치된다.
- 겹칠 수 있는 유일한 레이아웃은 아니다(RelativeLayout도 겹쳐서 표현할 수 있다.)
- 단, FrameLayout은 자식 요소를 겹치는 것 자체가 목적이기 때문에 겹칠 때 FrameLayout을 권장한다.

## ScrollView
- 우리가 흔히 아는 손으로 슥슥 내리는 그 뷰
- 자식 요소를 하나만 가질 수 있다.
- 속성
    - scrollbars="none": 스크롤 바를 보이지 않게 만든다.
    - fillViewport="true": 항상, 늘, 언제나 박아 넣자(구글의 설계 미스)

## ImageView
- 말 그대로 이미지를 보여주는 뷰 컴포턴트
- html 이미지 태그와 사용법이 유사하다
- 문제는 핸드폰 별로 사양이 제각각이고 고화질 이미지를 보기 어려운 단말들도 존재한다.
- 이 문제를 해결하기 위해 보통 같은 이미지를 해상도 별로 준비한다.
- 'android drawable importer' 플러그인으로 위의 동작을 편리하게 처리할 수 있다.
- 속성
    - scaleType: 비율을 유지한 상태에서 이미지의 크기, 위치를 변경시킴 (보통 centerCrop을 많이 사용한다)

## Drawable
- 직접 xml로 그림을 만들 수 있다.
- 최상위 뷰에는 android 경로를 늘 설정해야 한다. (다른 layout view xml도 마찬가지)
- 그라데이션은 보통 디자인 리소스가 아니라 이렇게 만드는 경우가 많다.
- 이미지의 경우 기종에 따라 깨질 수 있기 때문이다.
- 간단한 그림은 수치를 받아서 직접 만드는 게 좋을 때가 많다
