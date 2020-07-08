# 자바 멀티쓰레드
## 1. 개요
### Runnable vs. Callable
- Runnable: 비동기로 실행할 수 있는 태스크. 실행만 한다.
- Callable: 결과를 내는 태스크. 실행 뒤 결과를 반드시 낸다.

### Executor vs. ExecutorService
- Executor: Runnable 인터페이스의 실행을 스케줄링 한다. 스케줄링 하고 실행만 시킨다.
- ExecutorService: Callable 인스턴스를 한 개 이상 제출하고 결과가 나오면 결합할 수 있다.

## 2.1. 병행 태스크
- 병렬로 실행되는 작업을 다룬다.
### 1. Task
- 보통 Runnable 인터페이스에 등록된 작업을 의미한다.
```java
public interface Runnable {
    void run();
}
```
- 태스크 별로 쓰레드 한 개씩 할당하는 건 추천하지 않는다.
    - 비교적 계산이 간단한 태스크는 한 쓰레드가 여러개를 처리하도록 해라
    - 강도 높은 계산 수행 시에는 프로세서 별로 쓰레드를 하나씩 할당하고 컨텍스트 스위칭을 피하는 걸 권장한다.
        - (뭔소리야...)
### 2. Executor
- 태스크를 수행할 쓰레드를 관리한다.
- Executors.newCachedThreadPool(): 자동으로 최적화 된 실행자를 결과로 준다.
- Executors.newFixedThreadPool(int n): 수동으로 사용할 쓰레드 수를 지정할 수 있다. 

### 3. Future
- 언제 실행될 지 모르지만, 아무튼 언젠가는 실행되는 객체 + 멀티 쓰레드 병렬 작업 가능
- Callable
    - 태스크의 한 종류. Runnable과 다르게 무조건 결과를 반환한다.
    - 무조건 결과를 반환하므로, 잘못된 경우 예외라도 던진다.
        ```java
        public interface Callable<V> {
            V call() throws Exception;
        ```
- ExecutorService
    - Callable 태스크를 스케줄링 할 수 있다.
    - submit 된 태스크에 대해 Future 객체를 반환한다.
        ```java
        ExecutorService exec = Executors.newFixedThreadPool();
        Callable<V> task = ...;
        Future<V> result exec.submit(task);
        ```
