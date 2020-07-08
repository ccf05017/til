# 실습 진행
## 진행 방식
- http-go-deploy-v1.yaml을 배포하고 v2, v3로 순차적으로 롤링 업데이트를 해본다

## 실습 진행
### 1. v1 deployment 배포
- 아래 명령어로 실행한다.
    ```shell
    kubectl create -f http-go-deploy-v1.yaml --record=true
    ```
- `--record=true` 옵션을 반드시 준다.
    - 이 옵션이 없으면 history 저장이 되지 않는다.

### 2. 배포 상태 확인
- rollout 객체를 통해 확인할 수 있다.
- 상태 확인
    ```shell
    kubectl rollout status deploy {deployment name}
    ```
- 히스토리(revision) 확인
    ```shell
    kubectl rollout history deploy {deployment name}
    ```

### 3. 관찰을 위해 patch 명령어로 실행 지연
- 아래 명령어가 없으면 업데이트가 너무 빨리 진행된다
    ```shell
    kubectl patch deploy {deploy name} -p '{"spec":{"minReadySeconds": 10}}'
    ```
- 마지막의 내용들은 json 형태로 yaml 파일 설정을 변경하게 된다.

### 4. 로드 밸런싱을 위해 서비스 생성
- 아래 명령어로 외부 노출
    ```shell
    kubectl expose deploy http-go
    ```
- 서비스 정보를 확인해서 노출된 IP 확인할 것

### 5. 확인용 shell 스크립트 작성
- 테스트용 busybox bash 생성
    ```shell
    kubectl run -it --rm --image busybox -- bash
    ```
- 아래 shell 스크립트로 1초마다 서비스 상태 확인
    ```shell
    while true; do wget -O- -q 10.56.14.226:8080; sleep 1; done
    ```

### 6.1. set image로 업데이트 수행
- 간단하게 아래 명령어로 image를 바꿔서 롤링 업데이트를 진행할 수 있다.
    ```shell
    kubectl set image deploy http-go http-go=gasbugs/http-go:v2 --record=true
    ```
- 위의 명령어도 `--record=true` 명렁어를 잊지 않고 수행해야 한다.
    - 안하면 위 명령어가 아니라 http-go deployment의 처음 명령어를 그대로 입력한다.
    - 향후 관리 시 history 추적이 매우 어려워진다.
- record 옵션 true일 때의 예시
    ```shell
    deployment.extensions/http-go 
    REVISION  CHANGE-CAUSE
    1         kubectl create --filename=http-go-deploy-v1.yaml --record=true
    2         kubectl set image deploy http-go http-go=gasbugs/http-go:v2 --record=true
    ```

### 6.2. edit을 통해 yaml을 수정해서 업데이트 수행
- 위의 간단한 방법도 결국 yaml을 수행하는 거라 같은 효과를 낸다.
- 아래 명령어를 통해 yaml에서 image를 http-go:v3로 변경해준다.
    ```shell
    kubectl edit deploy http-go --record=true
    ```

### 7. undo로 롤백하기
- rollout 객체에서 실행할 수 있다.
    ```shell
    kubectl rollout undo deploy http-go
    ```
- 특정 버전으로 롤백하기
    ```shell
    kubectl rollout undo deploy http-go --to-revision={revision number}
    ```
