# Mockito + BeforeAll 삽질기
## 1. 개요
- 테스트 과정에서 종종 비싼 객체를 사용하게 된다 (MockMvc, Regex Pattern 등)
- BeforeEach 같은 걸로 비싼 객체를 초기화 하면 당연히 비효율적이다.
- 테스트가 적을 때는 상관없지만, 나중에 문제가 발생하기 때문에 BeforeAll로 한번만 초기화 하는 게 좋다.

## 2. 비싼 객체 잘 쓰는 법
- 한번만 초기화 하고 싶으면 BeforeAll 어노테이션이 선언된 메서드를 사용하는 게 편하다.
- 문제는 static만 BeforeAll의 혜택을 볼 수 있다. static 천지가 된다.
- 이게 싫으면 테스트 클래스에 TestInstance 어노테이션에 라이프사이클을 Per Class로 선언하면 된다.

## 3. Mock 객체와 위의 방법 조합 시 유의사항
- Mock 객체를 사용하는 방법에는 ExtendWith 어노테이션 사용방법과 MockitoAnnotation.initMock 메서드를 사용하는 방법이 있다.
- 이 중 'ExtendedWith' 어노테이션을 사용하면 'BeforeAll' 어노테이션 선언 된 메서드가 실행 된 뒤에 Mock 객체를 초기화 한다.
- 즉, BeforeAll 선언 된 메서드 안에서 Mock 객체를 사용하면 null 예외가 발생한다.(아직 초기화 안됐으니까)
- 이를 방지하기 위해 BeforeAll 선언된 메서드 안에서 Mock 객체를 사용하는 비싼 객체를 사용하고 싶다면 반드시 'MockitoAnnotation.initMock'
메서드를 통해 Mock 객체를 초기화 시킨 후 비싼 객체 생성자에 인자로 넘겨줘라
