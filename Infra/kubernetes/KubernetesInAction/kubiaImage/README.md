# 삽질 기록
## 도커 허브에 이미지 업로드하기
- 로그인을 먼저 해야 한다
    ```bash
    docker login
    ```
## GCP 쿠버네티스 클러스터 정보 확인
``` bash
kubectl cluster-info
```

## 의문점
- 로드밸런서 포트 뒤에 달려있는 컨테이너 포드는 무엇일까?
    - 노드 포트인 줄 알았는데 그렇기에는 해당 노드포트로 접속 불가능
    - 내부 포트로 추정 클러스터 내부에서 클러스터 내부 IP로 접속 시 응답옴
