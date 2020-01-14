# Currency Conversion Service 데모
## 특이사항
- RestTemplate을 사용하여 Currency Exchange Service를 소비해서 서비스를 제공한다
- 위의 방법은 넘나 귀찮기 때문에 Feign을 통해 간단하게 해결 할 수 있다.
- Ribbon을 통해 클라이언트 로드 밸런싱을 할 수 있다.
- 기본적으로 라운드로빈으로 로드밸런싱 처리한다.
