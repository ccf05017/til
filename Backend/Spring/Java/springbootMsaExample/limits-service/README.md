# Limits Service
- 각 서버들이 컨피그 받기 전에 컨피그 요청 한계를 확인할 수 있는 서비스
- Web, Actuator, Config Client 의존성이 있다.
- 기본적으로 8888 포트에 컨피그 받기 요청을 보낸다.
- 없다면 현재 bootstrap.yml 파일을 바라본다.
- config 설정이 바뀌면 새로고침을 진행해야 바뀐 내용이 반영된다.
- POST {host}/actuator/refresh
