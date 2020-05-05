# 스테이트풀셋
## 삽질기
- YAML 인덴트는 늘 주의하자. 자세한 건 문서 참조

## 주의사항

## 참고사항
- 레플리카셋과 다르게 한번에 하나씩만 만들어진다.
    - 레플리카셋은 한방에 주르륵 생성됨
- busybox로 url로 접근해보자
    ```bash
    kubectl run -i --tty --image busybox:1.28 dns-test --restart=Never --rm /bin/sh

    nslookup web-{number}.nginx.default.svc
    ```
- 스테이트풀셋을 통째로 날려도 PV, PVC는 여전히 남아있다.
    - 다시 동일한 스테이트풀셋을 실행하면 기존의 PV, PVC와 연결된다.
