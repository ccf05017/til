# Zuul Demo

## 사용법
- Gateway 서버 포트로 요청하는 URL에 대해서만 처리한다. (여기서는 8765)
- 그냥 바로 다른 마이크로 서비스 요청하면 GW 타지 않고 처리된다.
- 아래와 같은 방법으로 사용 가능
- HTTP_METHOD {host}:{zuul-port}/{service-name}/{서비스 URL}
- 모든 API 앞에 붙는 AOP같은 녀석? 
