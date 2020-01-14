# Currency Exchange Service Demo
## 특이 사항
- Environment 인터페이스를 이용해 현재 구동 중인 서버의 포트 번호를 응답함
- IDE에서 Spring Boot 실행 시 VM 옵션에 '-DSever.port' 옵션을 통해 다른 포트에 서비스를 띄울 수 있다.
- 데이터 소스 없으면 자동으로 test용 메모리 DB로 들어간다.(jdbc:h2:mem:testdb)

## 제공 기능
- GET /currency-exchange/from/{from}/to/{to}


