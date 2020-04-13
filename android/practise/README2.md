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
- Intent의 종류
    - 명시적 Intent: 정확한 대상에게 요청한다.
        - 전달만 하는 요청 (이벤트 방식)
        - 리턴을 전달하는 요청
    - 암시적 Intent: 할 수 있는 놈 아무한테나 요청한다.
- 값 전달도 가능하다.
    - putExtra 메서드 사용 (key, value 쌍으로 관리된다)
- 예시 코드
    - Intent1 -> Intent2로 이동
    ```kotlin
    val moveIntent = Intent(this@IntentExample, IntentExample2::class.java)
    moveIntent.putExtra("number1", 1)
    moveIntent.putExtra("number2", 2)
    startActivity(moveIntent)
    ```
    ```kotlin
    val moveIntent2 = Intent(this@IntentExample, IntentExample2::class.java)
    moveIntent2.apply {
        this.putExtra("number1", 1)
        this.putExtra("number2", 2)
    }
    ```
- Intent는 스택 구조다
    - Intent를 쌓을 때마다 위로 올라가기 때문에 finish 메서드로 스택을 pop 시켜야 한다.
    ```kotlin
    val result = number1 + number2
    val resultIntent = Intent()
    resultIntent.apply {
        this.putExtra("result", result)
    }

    setResult(Activity.RESULT_OK, resultIntent)
    finish()
    ```
- 보낸 Intent의 결과를 받는 것도 가능하다.
    - 단, 보낼 때부터 받는 것도 가능한 Intent로 보냈어야 한다.
        - startActivityForResult 메서드를 사용해서 보내야 한다.
            - startActivityForResult({intent}, {requestCode})
            - requestCode가 일종의 channel 역할을 하게 되는 것 같다.
    - onActivityResult 메서드를 통해 결과를 받을 수 있다.
    - data 객체의 get 메서드를 통해 Intent 리턴값을 받을 수 있다.
- 예시
```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == 3000) {
        Log.d("requestCode", requestCode.toString())
        Log.d("resultCode", resultCode.toString())
        val result = data?.getIntExtra("result", 0)
        Log.d("result", result.toString())
    }

    super.onActivityResult(requestCode, resultCode, data)
}
```

## Context
- 문맥
- 액티비티가 가지고 있는 주변 정보
- ActivityManagerService에 접근하도록 해주는 역할
    - ActivityManagerService: 안드로이드에서 이미 구현해 둔 수많은 기능들
- 기본적으로 Activity를 만들면 AppCompatActivity를 상속받는데, 이게 Context를 상속 받고 있다.
- 다른 시스템의 경우 바로 시스템 콜이 가능하지만, 안드로이드의 경우 바로 접근이 불가능하다.
- 안드로이드의 경우 어플리케이션 한 개가 프로세스 1개를 의미하진 않기 때문이다.
- 안드로이드의 경우 ActivityManagerService가 시스템 콜을 관장하는 브릿지 역할을 하며, Context를 통해 어플리케이션이 여기 접촉할 수 있다.
- 컨텍스트는 앱이 생성 될 때 만들어진다.
- 또한 앱의 구성요소가 생성 될 때마다 만들어진다.
- 이렇게 생성되는 컨텍스트는 서로 `다른 객체`이며, 상위 컨텍스트의 정보는 하위 컨텍스트를 통해 접촉할 수 있다.
- Application Context > Activity Context
- https://blog.naver.com/huewu/110085457720

## Task
- 안드로이드는 기본적으로 stack으로 쌓인다.
- 여기서 쌓이는 단위가 Task
- Activity와 거의 유사함.
- Activity Stack 관리 방식
    - launchMode: 켜지는 방법을 자체적으로 갖고 있다.
            - 다중 허용 방식
                - Standard: 무조건 다중 허용
                - SingleTop: 조건부 다중 허용(열려고 하는 액티비티가 현재 액티비티라면 onNewIntent를 호출한다)
            - 다중 비허용 방식
                - SingleTask
                - SingleInstance
        - IntentFlag: 켜지는 방법을 지시한다.
            - FLAG_ACTIVITY_NEW_TASK
            - FLAG_ACTIVITY_SINGLE_TOP
            - FLAG_ACTIVITY_CLEAR_TOP
            - 기타 등등
- Task 스택 관리는 그냥 기본 설정을 가능한 쓰자 (IntentFlag나 LaunchMode는 완전히 알 때 사용하자)

## Fragment
- Activity 분할 책임 요소
- 라이프 사이클이 존재한다.
- Activity에 종속적이다.
- Activity의 구성 요소가 너무 많거나 길거나 클 경우 관리할 코드가 너무 많아진다.
- 또한 엄청난 종류의 디바이스 종류 때문에 화면 구성이 많이 틀어진다 => 뷰를 떼서 옮겨야 될 수 있다.
- 이를 해결하기 위해 Activity를 조각조각 쪼개서 구성한다 => 이게 Fragment
- 단, 관리 포인트가 엄청 늘어날 수 있다. 이건 유의해야 한다.
- 사용방법
    - View XML 에서 fragment 태그를 선언해서 사용한다.
        - Activity와 다르게 반드시 ID를 지정해야 한다.
    - 코틀린 코드를 통해 동적으로 추가할 수도 있다.
        - fragmentManager, transaction을 통해 사용 가능
        ```kotlin
        val fragmentOne : FragmentOne = FragmentOne()
        val fragmentManager : FragmentManager = supportFragmentManager

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragmentOne)
        fragmentTransaction.commit()
        ```
        - 작업을 끝내는 방법은 여러가지가 있다.
            - commit: 여유 될 때 적용해라
            - commitnow: 당장 적용해라
            - commitAllowingStateLoss: 복구가 안되도 상관 없으니 commit 하겠다.
    - fragment를 떼는 방법
        - detach: 떼면 다시 못 붙인다.
        - remove: 떼고 다시 붙일 수 있다.
    - Activity -> fragment 데이터 전달하는 방법
        - bundle을 만들어서 끼워줘야 한다.
        ```kotlin
        val bundle : Bundle = Bundle()
        bundle.putString("hello", "hello")
        fragmentOne.arguments = bundle
        ```
    - Fragment -> Activity 데이터 전달하는 방법
        - 자체 구현해야 한다.
        - 보통 리스너를 구현해서 사용한다.
        - 인터페이스를 통해 전달한다.
- Fragment 생성 생명주기가 Activity 생명주기보다 빨리 실행된다.
- 생명 주기 메서드
    - onCreateView: Fragment가 인터페이스를 처음으로 그릴 때 호출된다.
        - inflater: 뷰를 그려주는 역할
        - container: 해당 Fragment가 들어갈 위치의 부모 뷰

## NullSafety
- 코틀린은 기본적으로 null을 허용하지 않는다. (자바에서 이미 null 고생할만큼 해봤기 때문에)
- 그래서 이걸 강제로 null을 허용할 때는 타입 뒤에 `?`를 붙여준다
    ```kotlin
    // 원래 코드
    if (button != null) {
        button.setOnClickListener
    }

    // null safety 관련 문법
    button?.setOnClickListener
    ```
- null 허용된 녀석을 다시 null이 아니라고 확신할 때는 `!!`를 붙여준다.
    - null일 경우 컴파일 에러가 발생한다.
    - 물론 가능한 사용 안하는 게 좋다. 에러 위험이 높다.

## Resource
- res 폴더에서 관리된다.
- drawable, layout, values로 구분
- values는 color, string, style을 관리한다.
- 흔한 종류별 리소스 관리와 같다.
- 관리의 효율성을 위해 반드시 사용하는 게 좋다.

## Thread
- 작업 흐름
앱 실행 -----> Launcher Activity -----> -----> -----> 작업흐름

- 안드로이드 쓰레드
-> MainThread (자동으로 생성됨)
----------------------------------------------------------->
    -> Launcher Activity
                     -> Activity B
                                          -> 영상 재생
                                                 -> 기타 등등
- 안드로이드는 멀티 쓰레드를 지원한다. (=> Thread Safe 구현이 필요하다)
- 위의 Main Thread와 동시에 별도의 Thread 작업을 처리하도록 만들 수 있다.
- 안드로이드 Main Thread의 특징
    - 안드로이드  Main Thread는 UI Thread라고도 불리운다.
    - 사용자의 input을 받는 thread
    - 절대 정지시킬 수 없다. (앱이 끝날 때까지)
- 가능한 사용자 정의 thread를 만들고 사용하지 않는 게 좋다.
- thread 선언 및 실행 방법
```kotlin
val runnable: Runnable = Runnable {
    Log.d("thread-1", "thread is made")
}

val thread: Thread = Thread(runnable)

button.setOnClickListener {
    thread.start()
}
```
- runOnUiThread: 작업중이던 thread에서 메인 thread로 돌아가야 될 때 사용한다.

## List View
- 유사한 화면을 반복해서 보여주는 것 (우리가 아는 그 리스트 쭈르륵 나오는 화면)
- 세가지 방식으로 그릴 수 있다. (AddView, ListView, RecyclerView)
- AddView
    - 실제로 리스트뷰를 그리기 위해서 잘 사용되지 않는다.
    - 기본적인 원본은 AddView다. 다른 방식을 이해하려면 잘 이해해야 한다.
    - 생성 방법
        - 리스트로 만들고 싶은 아이템의 리스트를 준비한다
        - 인플레이터를 만든다
        - 인플레이터로 아이템 하나에 해당하는 뷰를 만들어준다
        - 위에서 만든 뷰를 컨테이너 뷰에 붙여준다.
    - AddView는 자동으로 스크롤이 적용되지 않는다. => 자료양이 많으면 꼭 스크롤 뷰를 만들어준다.
    - 예시
    ```kotlin
    private fun createPhoneBookList(phoneBook: PhoneBook) {
        val layoutInflater = LayoutInflater.from(this@PhoneBookActivity)
        val container = findViewById<LinearLayout>(R.id.phonebook_container)
        for (i in 0 until phoneBook.userList.size) {
            val view = layoutInflater.inflate(R.layout.phone_book_item, null)
            val personNameView = view.findViewById<TextView>(R.id.person_name)
            personNameView.text = phoneBook.userList[i].name
            addSetOnClickListener(phoneBook.userList[i], view)
            container.addView(view)
        }
    }
    ```
- ListView
    - 예전에 많이 사용했다.
    - 기존 AddView에서 인플레이터를 통해 컨테이너에 연결해주던 부분을 `Adapter`가 처리해준다.
    - 그리는 방식이 기존 AddView와 다르다.
        - AddView는 반복문을 통해 그리기 때문에 리스트 전체 갯수에 상관없이 한번에 다 그린다.
        - ListView는 보여지는 부분과 여유분만 먼저 그리고 필요한 경우 더 그린다.
    - 위의 차이점 때문에 AddView는 실제로는 거의 사용 안한다.
    - ListView를 그릴 때는 꼭 ViewHolder를 활용하자
- RecyclerView
    - 최근 가장 많이 사용하는 방식
    - ListView의 개선판 (ViewHolder를 내장하고 있다)
    - 앞의 두 방식에 비해 유연하다.
    - LayoutManager
        - Linear: 선형(가로, 세로 모두 지원)
        - Grid: 균일한 바둑판
        - StaggeredGrid: 균일하지 않은 바둑판

## TabLayout, Pager
- 둘이 같이 사용되는 경우가 많다.
- 따로 사용이 불가능하다는 의미가 절대 아님
- Pager
    - 화면을 넘겨주는 역할 수행
    - 수평 리스트 뷰 같은 놈
    - adapter가 추가적으로 필요하다.
    - Fragment로 구성된다
- TabLayout
    - 탭을 담당하는 역
    - 기본 제공 view가 아니다
- Listener
    - TabLayout과 Pager를 연결해주는 역할
    - Tab에 따라 연관된 Pager를 보여주거나, 보고 있는 Pager의 Tab이 어딘지 알려줄 수 있도록 해준다.

## SharedPreference
- DB와 비슷한 무언가
- SQLite를 지원하지만 SharedPreference도 많이 사용한다
- Key, Value 방식의 DB
- 사용자의 기호를 저장하고 공유하기 위한 용도로 많이 사용된다.
- 간단한 DB 용도기 때문에 진짜 DB처럼 사용하려면 SQLite로 가자
- Mode
    - MODE_PRIVATE: 생성한 앱에서만 사용 가능 (가장 많이 사용하는 모드)
    - MODE_WORLD_READABLE: 다른 앱에서 읽을 수 있다.
    - MODE_WORLD_WRITEABLE: 다른 앱에서 읽고 쓸 수 있다.
    - MODE_MULTI_PROCESS: 이미 호출되어 사용중인지 체크
    - MODE_APPEND: 기존 preference에 신규로 추가

## Realm
- 또 다른 DB
- RDB
- SharedPreference보다 복잡한 데이터를 처리할 수 있다.
- IOS도 지원된다.
- 한국어 지원도 잘 됨
- 외부 라이브러리이기 때문에 추가 필요하다
- 제공하는 기능이 더럽게 많으니까 제대로 쓰려면 문서 필독

## Async
- 그 유명한 비동기 처리 (non-blocking)
- 싱글 쓰레드 방식 아님 (백그라운드에서 별도의 쓰레드를 만들어서 처리)
- 사용법
    - AsyncTask 상속 받아서 구현
        - onPreExecute: 쓰레드 출발하기 전에 할 작업
        - doInBackground: 쓰레드가 할 작업
        - onProgressUpdate: 쓰레드에서 진행 중인 작업의 진행 상태를 메인 쓰레드에게 알려줄 때 사용
        - onPostExecute: 보조 쓰레드 작업이 모두 완료되고 메인 쓰레드로 돌아왔을 때 하는 작업
    - AsyncTask<params, progress, result>()
        - params: doInBackground에서 사용할 타입
        - progress: onProgressUpdate에서 사용할 타입
        - result: onPostExecut에서 사용할 타입
- 장점
    - 메인 쓰레드를 기다리게 할 필요 없다.
    - 백엔드에서 데이터를 받을 때 주로 사용하게 된다.
- 단점
    - 재사용이 불가능하다.
    - AsyncTask는 동시에 하나만 실행할 수 있다. (병렬 처리 불가능)
        - 동시에 두 개의 AsyncTask를 실행하면 첫번째 AsyncTask가 완료될 때까지 두번째 AsyncTask는 실행되지 않는다.
- 주의사항
    - 구현 된 Activity가 종료되더라도 같이 종료되지 않는다. (찌꺼기가 남는다.)
        - 라이프 사이클 메서드를 활용해서 Async Task를 실행한 Activity를 벗어날 때 Async를 정리하도록 해야 한다.
- 공식적으로 안드로이드는 동기 방식으로 네트워크 통신을 사용하지 못하도록 강제된다.
    - 아무런 조치 없이 사용 시 강제로 동기 방식 네트워크 사용 시 에러, 경고 메시지 발생

## 네트워크
- 통신 기능 전반을 통칭
- 아무리 스마트폰 성능이 좋아서 자체적으로 처리할 수 있다 하더라도 결국 상호 작용, 동기화 등을 위해 백엔드 통신이 필수적이다.
- 대부분 필요한 데이터를 받아오기 위해 사용되므로, 비동기 통신을 통해 처리되야 한다.
- Serialize, Deserialize 필수
    - 보통 gson으로 처리한다.
