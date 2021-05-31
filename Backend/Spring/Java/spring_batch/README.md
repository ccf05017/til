# Spring Batch 도메인 개념
## Job 도메인
### Job
- 특정 작업의 전체 배치 프로세스를 캡슐화한 엔티티
- 단순히 Step Instance의 컨테이너 개념
    - Step 결합, 재시작 속성 구성 등을 책임진다.

### Job Instance
- Job의 논리적인 실행
- 실제 로드되는 Job의 데이터와는 아무 관계가 없다. (이건 ItemReader에서 관여할 일)
- JobParameter에 의해 서로 구분된다.
- Instance 사용의 의미
    - 새 Job Instance를 사용한다: Batch 작업을 처음부터 시작한다.
    - 기존 Job Instance를 사용한다: 이전에 중지된 Batch 작업을 멈췄던 곳부터 이어서 시작한다.

### Job Execution
- Job Instance의 물리적인 실행
- Job Execution이 성공할 때까지 관련 Job Instance는 완료되지 않은 것으로 간주한다.
    - 해당 Job Instance의 재실행 여부를 판단할 수 있도록 해준다.

### 정리
- Job: Job이 무엇이며 어떻게 실행되야 하는지 책임진다.
- JobInstance: 올바른 재시작을 위해 실행을 그룹화하는 책임을 가진다.
- JobExecution: Batch 실행을 위한 기본 메커니즘을 제공하고, 관련 프로퍼티를 관리하고 유지하는 책임을 가진다.

## Step 도메인
### Step
- Batch 프로그램의 도메인 로직이 움직이는 곳
- 실제 Batch 처리를 정의하고 컨트롤한다.

### Step Execution
- Step의 실행 시도를 의미한다.
- 실제로 Step이 실행됐을 때만 해당 객체가 생성된다.
- 커밋/롤백 횟수, JobExecution, 트랜잭션 관련 데이터를 갖고 있다.
- 각각의 Step Execution은 Execution Context를 갖고 있으며 통계, 재시작 등과 관련된 정보가 있다.
- 주요 프로퍼티
    - status: 실행 상태를 나타내는 객체 (STARTED, FAILED, COMPLETED)
    - startTime: step을 실행할 때의 시스템 시간
    - endTime: step 실행이 종료될 때의 시스템 시간(성공 여부와 관계 없다)
    - exitStatus: 실행 결과를 나타내는 상태
    - readCount, writeCount: 이름 그대로
    - commitCount: 실행 중 커밋된 트랜잭션 수
    - rollbackCount: 처리된 트랜잭션 중 롤백된 횟수
    - readSkipCount, processSkipCount, writeSkipCount: 각각 이름에 맞는 상태에서 실패 시 스킵된 횟수
    - filterCount: ItemProcessor에 의해 필터링된 아이템 수

## ExecutionContext
- 프레임워크에서 유지/관리하는 키값 쌍의 컬렉션
- StepExecution 혹은 JobExecution 객체에 속하는 상태를 저장한다.
- 실행 자체에 대한 통계 데이터도 ExecutionContext를 이용할 수 있다.
- 필요에 따라 적절하게 Job, Step의 ExecutionContext를 구분해서 사용하자.
- 주의사항
    - Executioncontext는 key를 공유한다. 절대 중복되지 않도록 주의하자
- ExecutionContext는 Job, Step 레벨 두 종류가 있다.
    - Step 레벨에서는 Step 내에서 커밋할 때마다 ExecutionContext에 저장한다.
    - Job 레벨에서는 Step 실행 사이사이마다 ExecutionContext에 저장한다.

## JobRepository
- 주요 개념체(Job, Step, JobLauncher) 구현체에 CRUD 기능을 제공한다.
- EnableBatchProcessing 어노테이션 사용 시 이 JobRepository를 자동으로 컴포넌트로 설정한다.

## JobLauncher
- 주어진 JobParameters로 Job을 실행하는 인터페이스
```java
public interface JobLauncher {
    public JobExecution run(Job job, JobParameters jobParameters) throws ${Job 관련 Exceptions} {

    }
}
```

## Item 처리
- Step에서 처리할 데이터들을 읽고, 처리하고, 쓰는 역할을 수행한다.
- ItemReader
    - Step에서 한번에 아이템을 하나씩 읽어오는 작업을 책임진다.
    - 더이상 읽을 아이템이 없으면 null을 반환한다.
- ItemWriter
    - Batch나 Chunk 단위로 아이템을 출력한다.
    - 현재 처리하기 위해 받은 아이템 정보만 알 수 있다.
- ItemProcessor
    - 아이템을 처리하는 비즈니스 로직을 책임진다.
    - 데이터 변환, 비즈니스 처리를 주로 수행한다.
    - 유효하지 않은 아이템은 null을 반환한다. (이 아이템은 write 되지 않는다.)
