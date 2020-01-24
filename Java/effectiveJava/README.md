# Effective Java 정리
## 1. 객체 생성과 파괴에 대해
## Item 1. 정적 팩토리 메서드
- 무작정 생성자를 만들지 말고 정적 팩토리 메서드 구현을 고려해라
### 언제 쓸까?
- 시그니처가 같은 생성자가 여러개 필요할 때 고려하면 좋다.
### 이점
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
### 단점
- 개발자 입장에서 찾기가 어렵다.
### 단점 해결방안
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

### 결론
- public constructor를 무조건 쓰지 말라는 게 아니다.
- 상황에 정적 팩토리 메서드와 public constructor 중 더 유리한 걸 골라서 쓰란거다.
- 하지만 보통 정적 팩토리 메서드가 유리한 경우가 더 많지롱 :)
