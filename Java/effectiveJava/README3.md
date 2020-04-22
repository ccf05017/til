## 3. 클래스와 인터페이스
- 자바 추상화의 핵심 근간
- 그만큼 지원하는 기능도 많다.

### Item 15. 클래스와 멤버의 접근 권한을 최소화하라
#### 왜?
- 소프트웨어의 설계의 근간이 되는 원리는 캡슐화다.
- 이를 위해 컴포넌트 간에는 API로만 통신하고 서로의 구현으로부터 자유로워진다.
- 이 캡슐화가 잘 된 것이 곧 좋은 설계이고, 이 캡슐화를 지원하는 기본 도구가 `접근 권한`이다.
- 그러니 접근 권한을 잘 관리해서 최대한 캡슐화를 잘 해라

#### 기본 원칙
- 모든 클래스와 멤버의 접근성을 가능한 좁혀라
- 즉, 일단 다 private을 발라놓고 정 안될때 package-private -> public 순으로 공개 범위를 넓혀라

#### 클래스 레벨의 접근 권한
- 톱레벨 클래스, 인터페이스가 public으로 선언된다는 건 영원히 하위 호환을 관리하겠다는 의미다.
- 그러니 꼭 공개해야 될 기능 외에는 다 package-private으로 숨겨라
- 한 클래스에서만 사용되는 package-private 톱레벨 클래스나 인터페이스는 사용하는 클래스의 private static으로 중첩시켜라(Item24참조)

#### 멤버 레벨의 접근 권한
- 멤버의 정의: 클래스의 필드, 메서드, 중첩 클래스, 중첩 인터페이스
- package-private, protected의 가장 큰 차이점은 protected 접근자 멤버의 경우 하위 클래스에서도 접근 가능하다.
- 즉, public 클래스의 protected 멤버는 공개 API나 다름 없다. -> 영원히 관리하며 고통받아야 한다.
    - 상속이나 기타 방안을 통해 외부 여기저기서 접근 가능하기 때문이다.
- 이런 사태를 피하고 싶으면 클래스 레벨 접근 권한과 마찬가지로 접근 범위를 최대한 좁혀라
- 다만, 제약 사항이 한가지 있다. 상위 클래스 메서드를 재정의할 때는 해당 멤버의 접근 권한을 상위 클래스에서보다 좁게 설정할 수 없다.
    - 즉, 인터페이스를 구현하는 경우에는 모든 인터페이스 구현 메서드들을 public으로 지정해야 한다.
- 테스트를 위해 범위를 넓히는 것은 package-private 까지만이다.
    - 그 이상 넓히는 건 설계를 잘못한 거다. 다시 생각해라.
- public 클래스의 인스턴스 필드는 모조리 private 바르고 시작해라
    - 가변 객체를 참조하거나 final이 아닌 인스턴스 필드가 public이면 불변식을 유지시킬 방법이 없다.
    - 스레드 안전하게 만들기가 굉장히 힘든 구조다.
    - 정적 필드라고 예외가 아니다 일단 다 private으로 만들어라
    - 딱 한가지 예외는 추상화된 의미가 있는 상수다. 얘만 public static final이 허용된다. (관례를 따라 구현하라)
- 위의 내용에서 특히 유의해아 할 사항은 `길이가 0이 아닌 배열은 모두 변경 가능하다`는 점이다.
    - 이런 게 공개되면 난리난다.
        ```java
        public static final Thing[] VALUES = {...};
        ```
    - 가능한 일급 컬렉션으로 감싸서 불변식을 유지하는 게 좋다.
    - 그게 아니라면 아래와 같은 방법들도 있다.
        ```java
        // public 배열을 private으로 만들고 public 불변 리스트를 추가한다.
        // 외부 공개용 불변 컬렉션을 만들어서 따로 제공하란 얘기
        private static final Thing[] PIRVATE_VALUES = {...};
        public static final List<Thing> VALUES =
            Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));
        
        // 배열을 private으로 만들고 public 메서드에서는 복사본을 전달한다.
        private static final Thing[] PRIVATE_VALUES = {...};
        public static final Thing[] values() {
            return PRIVATE_VALUES.clone();
        }
        ```

#### 그 외 방안
- 자바 9부터는 모듈 개념이 추가됐다.
- 근데 가능한 쓰지 마라(사실 이해도 안됨..)
