# Limits Service
- 각 서버들이 컨피그 받기 전에 컨피그 요청 한계를 확인할 수 있는 서비스
- Web, Actuator, Config Client 의존성이 있다.
- 기본적으로 8888 포트에 컨피그 파일 받기 요청을 보낸다.
- 없다면 현재 application.yml 파일을 바라본다.
