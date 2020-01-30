# Effective Java 정리
## 1. 객체 생성과 파괴에 대해
### Item 1. 정적 팩토리 메서드
- 무작정 생성자를 만들지 말고 정적 팩토리 메서드 구현을 고려해라
#### 언제 쓸까?
- 시그니처가 같은 생성자가 여러개 필요할 때 고려하면 좋다.
#### 이점
- 생성 코드의 의도를 드러냄으로써 명확한 코드를 작성하게 도와준다.
- 호출할 때마다 인스턴스를 새로 생성하지 않고 불필요한 객체 생성을 피할 수 있다.
- 인스턴스 라이프 사이클을 통제할 수 있다. => 유용한 패턴 구현(싱글턴 등)이 쉬워진다.
- 반환할 객체의 클래스를 자유롭게 선택할 수 있다.
- 이를 통해 클라이언트 코드가 대상을 구현이 아닌 인터페이스로만 다루게 만든다.
- Collctions 인터페이스 안에 여러 구현체들이 있는 것이 대표적인 예시다.
- 자바 8이후 구현이 좀 쉬워졌지만 아직까진 약간 까다롭다.
- 입력 매개 변수에 따라 매번 다른 구현체를 넘겨줄 수 있다.
- 이를 통해 마찬가지로 클라이언트 코드가 인터페이스에만 의존하도록 강제할 수 있다.
- 정적 팩토리 메서드 작성 시점에는 반환할 객체의 클래스가 없어도 되기 때문에 훨씬 구현이 유연하다.
- 생성자 없이 정적 팩토리 메서드만 있다면 상속이 불가능하기 때문에 Composition을 강요할 수 있다.
#### 단점
- 개발자 입장에서 찾기가 어렵다.
#### 단점 해결방안
- 관례에 따른 이름을 짓는다.
```java
// from: 매개변수를 하나 받아서 해당 타입의 인스턴스 반환
Date d = Date.from(instant);

// of: 여러개의 매개변수를 받아서 적합한 타입의 인스턴스 반환
Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);

// valueOf: from, of의 더 자세한 버전
BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);

// instance, getInstance: 매개변수로 명시한 인스턴스를 반환하지만, 동일성은 보장하지 않는다.
StackWalker luke = StackWaler.getInstance(options);

// create, newInstance: 위와 같지만, 매번 새로운 인스턴스를 반환한다.
Object newArray = Array.newInstance(classObject, arrayLen);

// getType: getInstance와 같지만 생성할 클래스가 아닌 다른 클래스에 정적 팩토리 매서드가 존재한다.
FileStore fs = Files.getFileStore(path);

// newType: newInstance와 같지만 생성할 클래스가 아닌 다른 클래스에 정적 팩토리 매서드가 존재한다.
BufferedReader br = Files.newBufferedReader(path);

// type: getType, newType의 간결한 버전
List<Complaint> litany = Collections.list(legacyLitany)
```

#### 결론
- public constructor를 무조건 쓰지 말라는 게 아니다.
- 상황에 정적 팩토리 메서드와 public constructor 중 더 유리한 걸 골라서 쓰란거다.
- 하지만 보통 정적 팩토리 메서드가 유리한 경우가 더 많지롱 :)

### Item 2. 빌더
#### 언제 쓸까?
- 생성자에 선택적 매개변수가 많을 때

#### 대안 1. 점층적 생성자 패턴
- 매개 변수에 따라 발생 가능한 경우의 수를 모두 생성자로 만드는 방법
- 생성자가 더럽게 많아지고 필요없는 변수까지 초기화해줘야 할 수도 있다.

#### 대안 2. 자바 빈즈 패턴
- 매개 변수가 없는 생성자로 객체를 만들고 setter를 사용한다.
- 안티 패턴. 쓰지 말아라.
- 불변 객체 생성이 매우 어렵다 -> 스레드 안전성 확보가 매우 어렵다.
- 객체가 완전 생성 전까지 일관성이 무너진다.

#### *참고
- 불변식: 프로그램이 실행되는 동안 혹은 정해진 기간 동안 반드시 만족해야 될 조건. 가변 객체에도 존재할 수 있다.
- 불변: 어떠한 변경도 허용하지 않는 상태. 불변식의 극단적 형태.

#### 그러니 빌더를 씁시다
- 필수 매개변수만으로 생성자나 정적 팩토리 메소드를 호출해서 빌더 객체를 얻는다.
- 빌더 객체를 통해 객체의 상태를 설정하고 build 메소드를 통해 완성형을 제공한다.
- lombok으로 쉽게 구현도 가능하다.
- 클라이언트 코드 예시
```java
BuilderExample builderExample = BuilderExample.builder(1, 3).calories(2).build();
```
- 빌더 패턴 구현 시 불변성을 보장할 수 있도록 유효성 검증 코드는 가능한 넣는 것이 좋다.
- 계층적 구조 구현에 아주 유리하다.
- 클라이언트 코드가 상위 클래스 타입을 신경쓰지 않고 바로 하위 클래스 타입으로 편리하게 사용 가능하다.

#### 결론
- 매개변수가 4개 이하면 사용 효용성이 낮을 수 있다.
- 하지만 보통 매개변수는 날이 갈수록 늘어나고 그때 빌더로 바꾸면 귀찮을 것이다.
- 그러니 그냥 이것도 시작할 때 앵간하면 빌더 바르면서 시작하는 게 편할거다.
- 그리고 코틀린에서는 별로 필요 없다. 이미 다 해준다.

### Item 3. 싱글톤
#### 언제 쓸까?
- 오직 하나만 객체가 생성되야 하는 모든 경우
- stateless object나 시스템 컴포넌트 구현이 필요한 경우가 대부분 

#### 방법 1.
- public static final 필드를 통해 초기화 한다.
- 아주 단순하게 구현 가능하다.
- API에 명백하게 싱글톤이라는 게 보인다.
```java
public class SingletonExample1 {

    public static final SingletonExample1 INSTANCE = new SingletonExample1();
    private SingletonExample1() {}
}
```

#### 방법 2.
- 정적 팩토리 메서드를 통해 싱글톤 객체 멤버를 제공한다.
- 싱글톤이 아니게 바꾸는 게 간단하다.(유연함)
- 제네릭 싱글턴 팩토리로 변경이 쉽다.
- 정적 팩토리 메서드를 공급자로 사용 가능하다.
```java
public class SingletonExample2 {

    private static final SingletonExample2 INSTANCE = new SingletonExample2();
    private SingletonExample2() {}   
    public static SingletonExample2 getInstance() { return INSTANCE; }
}
```

#### 문제점?
- 리플렉션에 대한 대책이 필요하다.
- 또한 직렬화, 역직렬화를 할 때 새로운 객체를 생성할 위험이 있다.

#### 해결책
- 리플렉션을 통해 새 객체 생성 시 에러를 던져 버린다.
- readResolve 메서드 구현을 통해 싱글톤임을 보장한다.
- 문제는 둘 다 매우 몹시 귀찮다.

#### 가장 추천되는 방안!
- 속성이 한 개인 enum으로 선언해라. 자동으로 싱글톤이 된다.

#### 결론
- 싱글톤이 필요하세요? 일단 enum 쓰고 안되면 클래스로 구현합시다.
- 참고: https://www.baeldung.com/a-guide-to-java-enums

### Item 4. static만 있는 클래스의 인스턴스화를 막자
#### 왜 static만 있는 클래스를 만들지?
- 특정 인터페이스를 구현하는 객체를 생성해주는 정적 메서드 or 팩토리를 모아놓을 때
- final 클래스와 관련된 메서드들을 모아놓을 때

#### 이걸 왜 인스턴스화를 막지?
- 애당초 static만 있다는 코드 자체가 인스턴스화 할 필요가 없는 코드라는 의미다.
- 이걸 인스턴스화 시킨다는 건 누가봐도 명백한 실수.
- 이런 실수를 방지하기 위해 인스턴스화를 막아주는 게 좋다.

#### 어떻게 막나?
- 간단하게 생성자를 private으로 넣어 버리자.
- 기본 생성자가 public으로 생성되는 걸 막아준다.
- 상속도 막아주는 부가 효과가 있다.
- 추상 클래스로는 막을 수 없다. 상속받아서 구현해버리면 끝이다.
- 직관적인 코드는 아니기 때문에 생성자에 주석으로 의도를 남겨주자 
