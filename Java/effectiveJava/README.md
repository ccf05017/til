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

### Item 5. 의존 객체 주입을 사용하자
#### 왜 쓰나?
- 많은 클래스가 하나 이상의 자원에 의존한다.
- 또한 이 의존 자원에 따라 동작이 변경되는 경우가 많다.
- 이 때 정적 유틸리티나 싱글톤을 사용하면 피를 볼 수 있다. (테스트 어려움 + 유연하지 못함)

#### 그럼 어떻게 해야 하나?
- Spring의 DI에서 노상 사용하던 의존 객체 주입을 사용하자.
- 생성할 때 필요한 자원을 넘겨주면 유연하고 테스트하기 용이한 코드를 만들 수 있다.
- 또한 자연스럽게 불변을 보장하므로 여러 클라이언트가 의존 객체를 안심하고 사용할 수 있다.
- 예시
```java
public class SpellChecker() {
    private final Lexicon dict;

    public SpellChecker(Lexicon dict) {
        this.dict = Objects.requireNonNull(dict);
    }
}
```

#### 단점
- 대규모 프로젝트에서는 이 의존 관계가 얽히면서 또다른 지옥도를 보여준다.

#### 해결책
- 우리에겐 Spring이 있다 :)
- 그 외에 대거나 쥬스도 있다. 나중에 한번 보자

#### 결론
- 클래스가 하나 이상의 자원에 의존할 때
- 그 자원이 클래스 동작에 영향을 줄 때
- 이럴 때는 무조건 의존 객체 주입을 써라
- 또한 의존하는 객체를 클래스 내부에서 생성하는 멍청한 짓도 절대 하지 말아라!

### Item 6. 불필요한 객체 생성을 피하자
#### 개요
- 똑같은 기능의 객체를 반복적으로 사용한다면, 단일 객체 재사용을 고려해볼만 하다.
- 특히 불변 객체는 재사용해도 안전하다는 게 분명하기 때문에 가능한 재사용하는 게 좋다.

#### 상황 1.
- 생성자에 넘겨지는 인자 자체가 생성자로 만들어내려는 객체와 기능적으로 같은 경우
- String이 가장 좋은 예시다.
```java
String doNotUseLikeThis = new String("Don't use");

String useLikeThis = "Use this";
```
- 위와 같이 구현하면 같은 JVM 내에서 똑같은 문자열 리터럴을 사용하는 모든 코드가 같은 객체를 사용한다는 게 보장된다.

#### 상황 2.
- 불변 클래스를 사용하는 경우
- 불변 클래스를 통해 만들어진 불변 객체를 사용할 때 정적 팩토리 메소드가 있다면 재사용성을 보장할 수 있다.
- 불변 객체이기 때문에 사용 중 상태가 변경되지 않으므로 안전하다.
- 가변 객체도 사용 중 상태가 변경되지 않다는 게 명확하면(이게 불변 아닌가 근데..?) 사용해도 된다.

#### 상황 3.
- 객체 생성 비용이 비싼 경우
- 이런 상황에서 객체를 반복적으로 생성하면 성능이 매우 떨어질 수 있다.
- 이럴 때는 클래스 초기화 과정에서 직접 생성해서 캐싱하고 나중에 필요할 때 재사용 하자.
- 물론 빈번하게 사용하지 않는 메서드에 이런 걸 적용하면 손해다.
- 필요할 때만 초기화시키는 지연 초기화로 이런 단점을 해소할 수 있다.
- 권하진 않는다. 지연 초기화는 더럽게 복잡한데 효과가 좋지 않을 때가 많다.

#### 상황 4.
- 오토박싱을 사용하는 경우
    - 오토박싱: 기본 타입과 박싱된 기본 타입을 섞어 쓸 때 자동으로 상호 변환해주는 기술
- 의도치 않게 사용하면 엄청나게 많은 성능 저하를 만든다.
```java
Long sum = 0L;
    
    public Long sum() {
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            sum += i;       // 이 부분에서 오토박싱이 발생한다.
        }
        return sum;
    }
```
- 위의 예제 같은 경우 sum이 엄청나게 많이 만들어질 것이다.
- 그냥 박싱된 기본타입이 꼭 필요할 때 아니면 까불지 말고 기본타입을 쓰자
- 박싱된 기본타입을 쓸 때는 '반드시' 의도치 않은 오토박싱이 없는지 확인하자

#### 유의사항
- '객체 생성은 무조건 피하고 재사용하자!'로 오해하지 말자 제발
- 요즘은 성능이 짱짱 좋기 때문에 간단한 객체 정도는 만들고 없애도 별 문제 없다.
- 객체 생성이 진짜 무거운 객체가 아니면 가능한 나만의 객체 풀 만들지 말자
- 내가 JVM보다 훨씬 똑똑하다 생각하면 해도 된다.
- '방어적 복사'와 대조적인 개념이다.
    - 방어적 복사: 새로운 객체를 만들어야 한다면 기존 객체를 재사용하지 마라!!
- 하지만 꼭 기억하자 방어적 복사가 필요한 상황에 구현하지 않으면 피똥 싼다.
- 반면 객체 재사용이 필요한 상황에서 안하면 피똥은 아니고 성능 문제가 좀 생길거다.

#### 결론
- 진짜 필요한 상황이 아니면 JVM을 믿고 객체를 새로 만들자.
- 불변 클래스 객체라 완벽하게 안전하거나 객체 생성 비용이 매우 클 때 고려해보고 사용하자

### Item 7. 다 쓴 객체는 참조를 해제하라
#### 개요
- 가비지 컬렉터가 있으면 메모리 관리 안해도 된다? -> 혼난다
- 오히려 객체 참조를 하나라도 놓치면 그 객체가 참조하는 모든 연쇄 객체를 놓친다. (치명적)
- 또한 이런 상황을 디버깅하기가 아주 까다롭다. 모르고 있다가 사고 터지고 알게 될 경우가 많다.

#### 상황 1.
- 클래스가 자기 메모리를 직접 관리하는 경우
- 클래스가 객체 참조를 담는 배열을 다루는 경우 문제가 자주 발생한다.(item7_MemoryLeakStack 참조)
```java
public class MemoryLeakStack {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public MemoryLeakStack(Object[] elements) {
        this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0)
            throw new EmptyStackException();

        return elements[--size]; // 여기서 메모리 누수가 발생.
    }

    private void ensureCapacity() {
        if (elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size + 1);
    }
}
```
- 위의 예제에서 pop을 수행해도 stack 범위 밖의 참조는 남아있고 이는 메모리 누수로 이어진다.

#### 해결책
- 가장 단순하게는 필요한 객체를 모두 사용한 뒤 객체 참조를 null로 바꿔주면 된다.
```java
public Object pop() {
        if (size == 0)
            throw new EmptyStackException();

        Object result = elements[--size];
        elements[size] == null; 
        return result
    }
```
- 사실 굉장히 안좋은 코드다. 예외적인 경우로 다뤄야 한다.
- 참조를 담은 변수를 유효 범위 밖으로 밀어내는 것이 가장 이상적이다.
- 이를 위해 변수의 범위를 최소가 되게 정의하는 게 좋다.
- 또한 약한 참조를 쓰면 도움이 될 것이다. (직접 메모리 관리 못하게)

#### 상황 2.
- 캐시를 사용할 때 메모리 누수가 많이 발생한다.
- 넣어놓고 있는지 모르고 까먹는 경우가 많다.

#### 해결책
- WeakHashMap을 사용하면 캐시 외부에서 key를 참조하는 동안만 유효하게 만들 수 있다.
- 시간이 지날수록 캐시 entry의 가치가 떨어지도록 구현하는 방법도 있다.
- 물론 캐시 엔트리를 청소하는 기능이 별도로 필요하다.

#### 상황 3.
- 리스너, 콜백을 사용할 때 많이 발생한다.
- 클라이언트가 콜백을 등록만하고 해제는 까먹었다? -> 메모리 누수

#### 해결책
- 콜백이나 리스너를 약한 참조로 저장하자.

#### 결론
- 가능한 약한 참조 사용하자.
- 클래스가 직접 메모리 관리하게 하지 말자.
- 캐시를 구현할 때는 잘 생각해보고 구현하자.

### Item 8. finalizer, cleaner는 사용하지 말아라
- 쓰지 말아라
- 한번 더 얘기하지만 쓰지 말아라

#### 뭐하는 녀석이길래?
- 객체 소멸자의 역할을 수행한다.
- C++의 소멸자와 같은 개념이 아니다. C++ 소멸자는 메모리 할당 해제를 위해 필수적이다.
- 자바에서는 이 역할을 GC가 맡는다.
- 객체 소멸자는 용도가 좀 다르다.

#### 왜 쓰지마?
- 언제 동작할 지 알 수 없다.
- 동작한다는 보장도 없다.
- 동작 중 발생한 예외는 무시하고 남은 작업이 있어도 에러 발생하자마자 종료된다.
- 심각한 성능 문제가 발생한다.
- finalizer 공격이라는 아주아주 심각한 보안상의 문제를 초래한다.
- AutoCloseable 인터페이스를 구현해라! 객체 소멸자는 절대 쓰지 말아라!

#### 쓸모가 아예 없나?
- 아주아주 제한적인 용도로 사용된다.
- 안정망 역할
    - 클라이언트 코드가 close()를 까먹고 안했을 때를 대비한다.
- 네이티브 피어 사용 시
    - 네이티브 피어는 자바 객체가 아니기 때문에 GC 대상이 아니다.
    - 단, 성능 저하가 상관 없고 즉시 닫아야 할 필요가 없는 상황에서만 사용해라
    - 위 상황이 아니면 close 메서드를 잊지 말고 써라!
    
#### 어떻게 쓰는지 보기나 하자(cleaner예시)
- 아래 코드에서 주의 사항은 State는 공유 자원이라는 점이다.
- 추가로 State는 절대로 RoomExample 인스턴스를 참조하면 안된다(순환참조가 되어 GC 대상에서 빠진다.)
- 이를 방지 하기 위해 내부 클래스에 static으로 State를 구현한다.
    - 자바에서는 정적이 아닌 중첩 클래스는 자동으로 바깥 객체의 참조를 갖는다
    - 람다도 마찬가지로 바깥 객체의 참조를 갖기 쉽다. 여기선 사용을 자제하자
```java
public class RoomExample implements AutoCloseable {

    private static final Cleaner cleaner = Cleaner.create();

    private static class State implements Runnable {

        private long numJunkPiles;

        State(long numJunkPiles) {
            this.numJunkPiles = numJunkPiles;
        }

        @Override
        public void run() {
            System.out.println("Clean");
            numJunkPiles = 0;
        }
    }

    private State state;

    private final Cleaner.Cleanable cleanable;

    public RoomExample(long numJunkPiles) {
        state = new State(numJunkPiles);
        cleanable = cleaner.register(this, state);
    }

    @Override
    public void close() throws Exception {
        cleanable.clean();
    }
}
```

- 물론 이렇게 한다고 cleaner가 매번 잘 돌 거라고 기대하면 안된다.
- 돌아갈 수도 있고 안돌아갈 수도 있고 자세한 건 실행시켜보면 안다.
```java
public static void main(String[] args) {
        new RoomExample(99);
        System.out.println("Maybe Clean..?");
    }
```

- 그러니 명시적으로 close 메서드를 사용하거나 try-with-resource를 사용해라
- 모범 예시 (AutoCloseable interface + try-with-resource)
```java
public static void main(String[] args) throws Exception {
        try (RoomExample roomExample = new RoomExample(7)) {
            System.out.println("Clean!");
        }
    }
```

#### 결론
- finalizer, cleanable은 믿을 수 없다. 절대 쓰지 마라
- 예방책으로만 상용해라.
- 이 마저도 성능이 안 중요할 때만 써라!!!!!
- AutoCloseable interface + try-with-resource 조합을 써라
- 최선의 방책은 close 메서드 사용을 까먹지 말아라

### Item 9. try-with-resource를 사용하자
- item8의 보완적인 내용이다.
- 기존에 꼭 닫아야 하는 자원들은 try, finally 조합이나 finalizer, cleaner를 사용했다.
- finalizer, cleaner는 쓰지 말라고 item8에서 지겹도록 얘기했다.
- 꼭 닫아야 하는 자원은 try-with-resource로 감싸서 사용하자

#### try finally는 왜 안좋은가?
- 일단 예외가 중첩되서 발생하는 경우 해당 자원을 정상적으로 종료시키지 못할 수도 있다.
- 또한 여러 에러가 발생했을 때 마지막 에러가 앞의 에러들을 덮어쓰기 때문에 디버깅하기 굉장히 까다롭다.
```java
public class TryFinallyExample {
    
    static String firstLineOfFile(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        try {
            return bufferedReader.readLine();       // 어떤 이유로 readline 메서드에 오류가 발생하면 close()가 실패한다.
        } finally {
            bufferedReader.close();
        }
    }
}
```

- 마지막으로 여러 자원을 닫아야 할 때 코드가 엄청 복잡하다.
```java
static void closeManyResources(String dst, String src) throws IOException {
        InputStream inputStream = new FileInputStream(src);
        try {
            OutputStream outputStream = new FileOutputStream(dst);
            try {
                byte[] buf = new byte[100];
                int n;
                while ((n = inputStream.read(buf)) >= 0)
                    outputStream.write(buf, 0, n);
            } finally {
                outputStream.close();
            }
        } finally {
            inputStream.close();
        }
    }
```

#### try-with-resource는?
- 닫아야 할 클래스에 AutoCloseable 인터페이스를 구현해주고, 사용할 때 try로 감싸기만 하면 된다.
- 어떤 상황에서도 자동으로 잘 닫아준다.
- 에러가 중첩되서 발생해도 suppressed라는 꼬리표를 달고 표기된다.
- getSuppressed 메서드를 통해 감춰진 에러를 볼 수 있다.
- 코드가 아주 깔끔해진다.
- catch를 사용 가능하다.

#### 결론
- 꼭 닫아야 할 자원을 구현한다면 AutoCloseable 인터페이스를 무조건 구현해라
- 꼭 닫아야 할 자원을 사용한다면 무조건 try-with-resources를 사용해라
- 예외는 없다. 무조건이다. 써라. 제발. 꼭.
