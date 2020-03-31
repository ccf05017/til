# 안드로이드
- 안드로이드가 어떻게 구성되어 있는지 확인해보자

## manifests
- 앱 전체의 지도같은 것
- 여길 보면 앱이 어떻게 동작하는지 대략적으로 알 수 있다.
- package 명은 유일해야 한다.(세상 전체를 다 따져서)
- 겹치면 배포 못한다.
- 주요 태그
    - application: 앱에 대한 내용물들을 적어놓는 곳
        - allowBackup: 앱을 삭제해도 일정 부분들을 백업하는 설정
        - icon: 앱을 설치했을 때 화면에 보이는 그 아이콘. 직사각형이다.
        - label: 앱의 이름
        - roundIcon: 핸드폰에 따라 앱 아이콘을 동그랗게 그리는 경우가 있다. 이것에 대응한다.
        - supportsRtl: 읽기 방향이 역인 국가(일본같은 곳)에 대응하기 위한 옵션
        - theme: 앱의 기본적인 스타일. 말 그대로 테
    - activity: 안드로이드의 화면
        - intent-filter:
            - launcher: 해당 화면을 런처(앱을 실행할 때 나오는 화면)으로 지정함.

## java 패키지
- 실제 앱 코드, 테스트 코드들이 위치한다.

## res
- 안드로이드에서 사용 될 리소스들이 모여있는 패키지
- drawable: 앱에서 사용하는 이미지들
- layout: 앱의 화면을 그릴 때 사용되는 xml 파일들
- mipmap: 앱의 아이콘들
- values: 리소스 공용 변수들
    - colors: 색상 모음집
    - strings: 글자 모음집
    - styles: 테마 모음집

## Activity
- 대략적으로 앱의 한 화면이라고 생각하면 된다.(정확한 설명은 아님)
- Life Cycle이 존재한다. (매우 중요)
    - 6가지 콜백으로 구성된 핵심 세트 (onCreate, onStart, onResume, onPause, onStop, onDestroy)
        - onCreate: 액티비티가 만들어질 때 단 한번만 호출됨 / 생성자와 매우 유사함
        - onResume: 액티비티가 다시 호출될 때 하면 되는 모든 작업들
        - onPause: 화면의 일부가 가려졌을 때
        - onStop: 화면 전부가 보이지 않을 때
        - onPause, onStop 후 다시 앱으로 돌아오면 onResume은 무조건 다시 실행하게 된다.
    - https://developer.android.com/guide/components/activities/activity-lifecycle
    - onPause, onStop은 명확하게 구분해서 사용하기 어렵다.
        - 그래서 보통 그냥 둘 중 하나 선택해서 하나에 다 밀어넣는다.
- IDE에서 제너레이트 기능으로 activity를 생성하면 manifests에 자동으로 추가된다.

## Xml 가져오기
- 익명함수를 이용하게 된다. (1회성으로 필요하기 때문)
- findViewById 함수로 View 타입을 액티비티로 불러올 수 있다. (자바에서 지원되던 구버전 사용법)
- 코틀린의 경우 View의 id를 통해 바로 임포트시킬 수 있다.

## EventHandler
- 자바스크립트 이벤트핸들러와 매우 유사하다.
- 엑티비티에서 XML 형태의 View 컴포넌트를 불러오고, 해당 컴포넌트에 이벤트 핸들러를 달아줄 수 있다.
- onClick, onScroll 등등 많은 이벤트가 있다. 자세한 건 문서 참조
- https://developer.android.com/guide/input
- 핸들러 작성 방법
    - 람다
    ```kotlin
    hello.setOnClickListener {
        Log.d("click", "Click!!")
    }
    ```

    - 익명 함수
    ```kotlin
    hello.setOnClickListener(object: View.OnClickListener {
        override fun onClick(v: View?) {
            Log.d("click", "Click!!")
        }
    })
    ```

    - 일반적인 방식
    ```kotlin
    val click = object: View.OnClickListener {
        override fun onClick(v: View?) {
            Log.d("click", "Click!!")
        }
    }
    hello.setOnClickListener(click)
    ```

## 뷰를 조작하는 방법
- 자바스크립트 처럼 세터나 이벤트 핸들러를 통해 동작을 제어할 수 있다.

## Intent
- 무언가를 요청하는 행위
- 사용 가능 관계
    - Activity <-> Activity
    - Android System <-> App
    - 다른 App <-> My App (상호 합의를 통해 사용 가능)
- 요청의 종류
    - 전달만 하는 요청 (이벤트 방식)
    - 리턴을 전달하는 요청
