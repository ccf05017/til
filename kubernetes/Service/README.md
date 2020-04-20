# Service
- 클라우드로 실습하는 걸 권장

## 참고사항
- Readiness 프로브가 정상작동하지 않으면, 서비스에 등록되지 않는다.
- Session Affinity 기본 timeout은 10800s(3H)
- 구글 클라우드 방화벽 오픈해야 함
    gcloud compute firewall-rules create http-go-svc-rul --allow=tcp:30001
