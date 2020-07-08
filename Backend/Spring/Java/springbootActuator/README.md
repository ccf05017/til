# Spring Boot Actuator 데모
## 목표
- Spring Boot Actuator가 어떻게 작동하는지 대략적으로 알아보기

## 현재 상태
- 설정 파일(application.yml)에서 모든 actuator 엔드 포인트가 나오도록 설정 된 상태
- Hal Browser를 통해 tcp 8080 포트로 접속 시 모든 엔드포인트 탐색 가능

## 사용 방법
- 해당 서비스 실행 후 http://localhost:8080 접속으로 Hal Browser 서비스 실행
- Explorer에 '/actuator' 입력 후 endpoint 탐색

## 주의 사항
- 이렇게 보안, 기타 설정 없이 운영단에 던지면 큰일난다. 혼난다.
- 공식 문서 참고해서 꼭 보안 설정하고 필요한 것만 노출시키자.
- https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html
