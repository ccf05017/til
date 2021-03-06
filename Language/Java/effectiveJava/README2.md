## 2. 모든 객체의 공통 메서드
- Object 구체 클래스가 모든 클래스에게 상속된다.
- 이 때문에 final이 아닌 메서드들(equals, hashCode, toString, clone 등)은 규약에 맞춰 재정의 해야한다.
- 안 그러면 오작동 할 수 있다.

### Item 10. equals는 일반 규약을 지켜서 재정의해라
#### 1. 개요
- 아예 재정의 안하는 걸 추천한다.
- 특히나 아래의 경우 중 한가지라도 속한다면 '절대 대정의하지 말아라'
    - 각 인스턴스가 본질적으로 고유하다. (동작을 표현하는 객체들)
    - '논리적으로 같은가'를 비교할 일이 절대 없다.
    - 상위 클래스에서 재정의한 equals가 하위 클래스에 딱 들어맞는다.
    - 클래스가 private, protected 접근자로 선언되어 있다.
    - equals 메서드는 때려죽여도 쓸 일이 없다.
- 위의 경우를 제외하면 아마도 DDD 전술 패턴의 VO를 구현할 때 equals 재정의가 필요할 것이다.
- 단, 그 중에서도 싱글톤과 enum은 재정의 할 필요가 없다. (비교 대상이 나타나지도 않을 것이다!)

#### 2. equal 재정의를 위한 일반 규약(반드시 지켜야 함)
- equals 메서드는 '동치관계'를 구현하며 아래 조건을 반드시 준수해야 한다.
    - 반사성: 객체는 자기 자신과 같아야 한다.(x.equals(x) = true)
    - 대칭성: 서로에 대한 동치 여부에 똑같이 답해야 한다(x.equals(y) = y.equals(x))
    - 추이성: 삼단논법을 통한 추론이 가능해야 한다.(x.equals(y) && y.equals(z) = x.equals(z))
    - 일관성: 한 번 같은편은 죽을 때까지 같은편, 한 번 원수는 죽을때까지 원수
    - null아님: x.equals(null) = false
    
##### 2.1 반사성
- 객체가 자기 자신과 같지 않게 만들어야 깨진다.
- 사실 깨기가 더 어렵다..

##### 2.2 대칭성
- 이 규약을 어기면 객체를 사용하는 다른 객체들의 반응을 예측할 수 없게 된다.
- 너무 과도하게 다른 객체와의 비교를 구현하려 할 때 깰 경우가 많다.
- 나랑 같은 타입일 때만 동등 비교하도록 구현하면 피할 수 있다.

##### 2.3 추이성
- 상속을 사용할 때 많이 실수하게 된다.
- 상위 클래스에 없는 새로운 필드를 하위 클래스에 추가하려다가 발생한다.
- 이걸 해결할 방법은 없다.
- 추상 클래스를 상속받거나 컴포지션을 사용해라. 그러면 자연스럽게 해결된다.

##### 2.4 일관성
- 불편 클래스의 경우 자연스럽게 지켜지기 때문에 큰 문제 없다.
- 가변 객체의 경우 equals 메서드 실행 중 신뢰할 수 없는 자원이 절대 끼어들어서는 안된다!!
- 자바의 URL 객체가 이런 걸 잘못 구현한 대표 사례다.

##### 2.5 null 아님
- 실수로 nullPointer 예외가 발생하는 것까지도 허용하지 않는다.
- 반드시 null 확인 절차를 넣어줘라.(더럽게 귀찮다. 코틀린은 안해도 된다.)
- 다만 명시적으로 구현하기 보다 equals 내에 묵시적으로 구현해라
```java
@Override
public boolean equals(Object obj) {
    if (!(obj instanceof NotNullExample))   // 묵시적인 null 확인
        return false;
    return false;
}
```

#### 3. 그러니 이런 순서로 구현해라
- == 연산자를 통해 입력이 자기 자신의 참조인지 확인해라
- instanceof 연산자로 입력이 올바른 타입인지 확인해라
- 입력을 올바른 타입으로 형변환 시켜라
- 입력 객체와 자기 자신의 대응되는 '핵심 필드'들이 '모두' 일치하는지 확인해라

#### 4. 기본 타입 비교는?
- 기본 타입은 == 연산자로 비교해라(float, double 제외)
- float, double은 compare 메서드를 이용해라(소수점 계산 때문)

#### 5. 참고 사항
- 가끔 null도 정상 값으로 취급하는 참조 타입 필드가 있다. 이 경우 NullPointer 예외 발생에 유의하자
- 아주 복잡한 필드를 가진 경우 표준형을 저장해두고 표준형끼리 비교하면 효율적이다.

#### 6. 주의사항
- equals를 재정의 하면 버릇처럼 hashCode도 재정의해라
- 너무 복잡하게 접근하지 말아라 만악의. 근원이다.
- Object 외의 타입을 equals의 매개변수로 받지 말아라.

#### 7. 결론
- 나보다 인텔리제이가 더 똑똑하니까 인텔리제이 자동완성을 쓰자
- 인텔리제이가 없다면 구글의 AutoValue 프레임워크를 사용하자
- 물론 그래도 잘 만들어졌나 확인할 수 있고, 어떻게 만들어진가 알수 있을 정도로는 공부하쟈

### Item11. equals를 재정의 했다면 hashCode도 재정의해라
#### 1. 왜?
- hashCode 재정의 없이 equal만 재정의 하면 HashMap, HashSet 컬렉션에서 문제가 발생한다.
- hashCode 관련 Object 명세
    - equals 비교에 사용되는 정보가 변경되지 않았다면, 애플리케이션이 실행되는 동안 그 객체의 hashCode 메서드는 몇번을 호출해도 같은 값을 반환해야 한다.
    - equals 메서드가 두 객체를 같다고 판단했다면, 두 객체의 hashCode는 똑같은 값을 반환해야 한다.
    - equals가 두 객체를 다르다고 판단해도 hashCode가 서로 다른 값을 반환할 필요는 없다.
    => hashCode가 같다고 무조건 같은 객체는 아니다. 하지만 논리적으로 같은 객체는 hashCode 반환값이 무조건 같아야 한다.

#### 2. 어떻게 만들어야 할까?
- 좋은 해시 함수는 서로 다른 인스턴스에 다른 해시코드를 반환한다.
- 이상적인 해시 함수는 주어진 인스턴스들을 32비트 정수 범위에 균일하게 분배해야 한다.
- 나쁜 예시
    ```java
    @Override public int hashCode() { return 42; }
    ```
    - 위와 같이 작성하면 거대한 링크드 리스트로 작성한 것처럼 동작한다
    - 더럽게 느려질 것이다
- 이상적인 구현을 위한 방법론
    - int 변수 result를 선언한 후 값 c로 초기화한다.
        - c는 해당 객체의 첫번째 핵심 필드를 아래 방식으로 계산한 해시 코드
    - 해당 객체의 나머지 핵심 필드들에 아래의 작업을 수행한다.
        - 해시 코드 계산
            - 기본 타입 필드: Type.hashCode(f)
            - 참조 타입 필드 + equals 메서드가 이 필드의 equals를 재귀적으로 호출하면 재귀적으로 호출한다.(뭔 말이야..)
            - 필드가 배열이라면 핵심 원소 각각을 별도 필드처럼 다룬다.
        - 위에서 계산한 해시코드 c로 result를 갱신한다.
            - result = 31 * result + c;
    - result를 반환한다.
    - 파생 필드는 해시 코드 계산에서 제외해도 된다.(= 다른 필드로부터 계산 가능하다면 생략해도 된다.)
    - equals 비교에 사용되지 않는 필드는 `반드시` 제외한다.
- result 갱신 식의 근거는 전통적인 해시 계산 방식(소수를 사용하는 등)의 어쩌구 저쩌구가 녹아 있으니 안심하고 쓰자

#### 3. 편하게 구현하려면?
- Object의 hash 메서드를 활용하면 된다.
    ```java
    @Override
        public int hashCode() {
            return Objects.hash(lineNum, prefix, areaCode);
        }
    ```
 
 #### 4. 좀 더 성능을 높일 수는 없을까?
 - 캐싱을 사용할 수 있다.
 - 혹은 지연 초기화 전략을 사용할 수 있다.
     ```java
    private int hashCode; // 자동으로 0으로 초기화된다.
        
        @Override
        public int hashCode() {
            int result = hashCode;
            if (result == 0) {
                result = Short.hashCode(areaCode);
                result = 31 * result + Short.hashCode(prefix);
                result = 31 * result + Short.hashCode(lineNum);
                hashCode = result;
            }
            return result;
        }
    ```

#### 5. 주의 사항
- 성능 높인다고 해시코드 계산할 때 멍청하게 핵심 필드 생략하지 말아라
    - 당장은 성능이 올라도 결국 해시 테이블 성능을 크게 떨어뜨릴 수 있다.
- hashCode가 반환하는 값의 생성 규칙을 API 사용자에게 자세히 공표하지 말자
    - 그래야 클라이언트가 이 값에 의지하지 않는다.
    - 그래야 추후에 더 좋은 계산식을 발견했을 때 수웛하게 고칠 수 있다.

#### 6. 결론
- 아주 민감하게 처리해야 되거나, 잘 아는 상태가 아니면 인텔리제이 기능을 사용하자
- 혹은 AutoValue 프레임워크를 사용하자

### Item12. toString을 항상 재정의하라
#### 왜?
- toString 메서드가 잘 정의된 시스템은 `디버깅하기 쉽다`
- 자바 기본 규약 중 toString 부분에 명시되어 있다.
    - 모든 하위 클래스에서 이 메서드를 재정의하라!!
    
#### 어떻게 구현해야 하나?
- 기본적으로 스스로 속한 객체를 완벽히 설명하는 문자열이어야 한다.
- 해당 객체의 `한 줄 요약` 정도로 생각하면 된다.
- 실제 사용할 때는 그 객체가 가진 모든 주요 정보가 표기되면 좋다.
- 물론 객체가 과하게 크거나 문자열로 표현하기 적합하지 않다면?
    - => 요약 정보만 담는다.
    
#### 고려사항
- toString의 반환값 포맷을 문서화할지 고민해야 한다.
- 문서화한다면 사용이 편리하지만, 향후 변경이 힘들어진다.
- 그러니 값 객체에만 포맷 지정하는 것이 좋다.
- 또한 포맷을 명시하던 안하던 `의도는 명확히 밝혀야 한다`

#### 주의사항
- 포맷 명시 여부와 상관 없이 toString에 있는 내용과 관련된 값에 얻어올 수 있는 API를 제공해라
    - => 주요 속성에 대한 접근 방법을 제시하란 얘기다(getter가 됐건 다른 방안이 됐건)
- 정적 유틸리티 클래스와 대부분의 열거 타입은 따로 재정의하지 않아도 된다.
- 그 외에는 전부 재정의해라!
- 특히 컬렉션 클래스는 재정의 꼭 해줘라.
    - 안하면 기본 내장 추상 컬렉션 클래스의 toString이 실행된다(메모리 주소 찍히는 그것)

### Item13. clone 재정의는 주의해서
#### Cloneable 인터페이스는 일단 쓰지 말아라
- Cloneable 인터페이스는 규약이 매우 허술하고, 깨지기 쉽고, 위험하고, 모순적인 구현을 강제한다.
- 그래도 하도 많이 쓰이기 때문에 어떻게 쓰는지는 알고 있어야 한다.
- 그래도 쓰지 마라. 이미 Cloneable 인터페이스로 clone이 재정의 된 객체가 아니면 쓰지 마라.
- 배열만이 여기서 예외다. 배열의 clone은 유일하게 올바른 구현법을 보여준다.

#### 구현 방안
- Cloneable 인터페이스 구현, clone override
- 접근 제한자 public, 반환 타입을 자기 자신으로 override
- super.clone을 호출한 후 필요한 필드를 모두 적절히 수정한다.
    - 객체의 깊은 구조에 숨어 있는 모든 가변 객체를 복사한다.
    - 복제본이 가진 객체 참조 모두가 복사된 객체를 가리키도록 한다.

#### 가장 간단한 경우
- 모든 필드가 기본 타입이거나 불변 객체를 참조할 때
- 별로 손댈 게 없다.
```java
@Override
public PhoneNumber clone() {
    try {
        return (PhoneNumber) super.clone();
    } catch (CloneNotSupportedException e) {
        e.printStackTrace();
    }
    return null;
}
```

#### 재앙으로 변하는 경우
- 가변 객체를 손대는 경우 아주 끔찍해진다.
- 재귀적으로 돌면서 다 카피해야 한다.
- 안 그러면 불변식을 해치는 상황이 발생한다.
```java
@Override
protected Stack clone() throws CloneNotSupportedException {
    Stack result = (Stack) super.clone();
    result.elements = elements.clone();
    return result;
}
```
- 위 상항보다 더 끔찍한 경우도 있다.
    - 링크드 리스트를 가변 객체로 갖고 있다던가 하면 호출해야 될 복사가 훨씬 많아진다.

#### 유의사항
- 재정의한 clone 메서드는 throws 절을 없애야 한다.
    - 안 그러면 사용하기 굉장히 불편하다.
- 상속용 클래스는 cloneable을 구현하면 안된다.
    - 구현할 경우 하위 객체에서 clone을 호출해도 상위 객체 타입을 리턴하게 된다.
    - 그래서 아래와 같이 아예 못쓰게 만드는 방법도 있다.
    ```java
    @Override
    protected final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    ```
- Cloneable을 구현한 스레드 안전 클래스를 작성할 때는 clone 메서드 동기화도 필수다.

#### 그럼 뭘 쓰란거냐?
- 어차피 clone 메서드는 생성자와 유사하게 동작한다.
- 그러니 `복사 생성자`와 `복사 팩터리`를 사용하자
- 이 방법은 위와 같이 복잡하지도 않고 문제 소지도 적다.
- 또한 인터페이스 타입을 인수로 받아서 처리할 수 있기 때문에 더 유연하기까지 하다
```java
// 복사 생성자
public Yum(Yum yum) {
    // 우리가 아는 바로 그 생성자 내용 그대로
} 

// 복사 팩토리
// 어차피 위의 생성자를 정적 팩터리 메서드로 바꿨을 뿐이다
public static Yum copyInstance(Yum yum) {
    // 생성자 내용 그대로
}
```

### Item14. Comparable 구현을 고려하자
#### 왜?
- Comparable을 구현하면 내장된 제니릭 알고리즘과 컬렉션을 손쉽게 사용할 수 있다.
- 사실 말이 '고려하자'이지 실질적으로 순서 비교가 있다면 무조건 구현해라
- '작은 노력으로 나중에 큰 효과를 누릴 수 있을 것이다'라고 저자가 추천했다.

#### compareTo 메서드 규약
- 아래의 규약을 지키지 않으면 정렬 알고리즘을 지원하는 내장 객체 사용이 불가능하다.
    - TreeSet, TreeMap, Collections, Arrays
- 해당 객체와 주어진 객체의 순서를 비교한다
- 해당 객체가 주어진 객체보다 작으면 음의 정수, 같으면 0, 크면 양의 정수를 반환한다.
- 타입이 다른 객체가 주어지면 ClassCastException을 던진다.
    - 처리가 꼭 필요한 경우에는 예외를 던지지 않고처리해도 된다.
- 아래 조건을 모두 충족해야 한다.
    - 두 객체 참조의 순서를 바꿔 비교해도 같은 결과가 나와야 한다.
    - 추이성이 있어야 한다.
        - 첫번째 객체가 두번째보다 크고 두번째가 세번째보다 크면 첫번째는 세번째보다 커야 한다.
    - 크기가 같은 객체 끼리는 어떤 객체와 비교해도 항상 같아야 한다.
    
#### 유의사항
- Comparable을 구현한 클래스를 확장해서 값 컴포넌트를 추가하고 싶다면 독립된 클래스를 만들어라
    - 기존 클래스를 확장한 구체 클래스에서 새로운 값 컴포넌트를 추가하면 compareTo 규약을 지킬 수 없다.
- compareTo 메서드로 수행한 동치성 테스트의 결과가 equals 메서드와 같도록 구현해야 한다.
    - 정렬된 컬렉션들은 동치성 비교에 equals 대신 compareTo를 사용하기 때문이다.
- compareTo 메서드는 각 필드의 동치성을 비교하는 게 아니라 `그 순서를 비교`한다.
- 종종 값의 차를 기준으로 compareTo를 구현하는 경우가 있다 => `하지 말아라`

#### 기본 비교자를 통한 구현
- 이미 compareTo가 구현된 객체나 기본 제공되는 경우 구현된 비교자를 사용한다
- 참조 필드가 하나인 경우
```java
public final class CaseInsensitiveString
    implements Comparable<CaseInsensitiveString> {
    public int compareTo(CaseInsensitiveString cis) {
        return String.CASE_INSENSITIVE_ORDER.compare(s, cis.s);
    }   
}
```
- 참조 필드가 여러개인 경우
    - 가장 핵심적인 필드부터 비교해 나간다.
    - 우선순위가 높은 필드가 동일할 경우 다음 순서로 계속해서 비교해 나간다.
```java
@Override
public int compareTo(PhoneNumber phoneNumber) {
    int result = Short.compare(areaCode, phoneNumber.areaCode);
    if (result == 0) {
        result = Short.compare(prefix, phoneNumber.prefix);
        if (result == 0) {
            result = Short.compare(lineNum, phoneNumber.lineNum);
        }
    }
    return result;
}
```

#### 비교자 생성 메서드를 통한 구현
- 기본 원시타입에 대해서는 내장된 comparing 메서드를 사용하면 된다.
```java
private static final Comparator<PhoneNumber> COMPARATOR = 
            comparingInt((PhoneNumber phoneNumber) -> phoneNumber.areaCode)
            .thenComparingInt(phoneNumber -> phoneNumber.prefix)
            .thenComparingInt(phoneNumber -> phoneNumber.lineNum);
    
@Override
public int compareTo(PhoneNumber phoneNumber) {
    return COMPARATOR.compare(this, phoneNumber);
}
```
- 객체 참조용 비교자 생성 메서드도 있기 때문에 객체 참조에는 해당 메서드를 사용하면 된다.
- 성능 저하가 약간 발생할 수 있다. 이건 염두해두자.
