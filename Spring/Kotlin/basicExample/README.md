# Kotlin + Spring CRUD 예시 프로젝트

## 실행
```shell script
./gradelw bootjar
java -jar ./build/libs/demo-0.0.1-SNAPSHOT.jar
```

## 테스트 가능 API
```shell script
# GET Collection
curl http://localhost:8080/customers

# GET Member
curl http://localhost:8080/customers/3

# POST
curl -d '{"id": 4,"name": "Tom"}' -H "Content-Type: application/json" -X POST http://localhost:8080/customers

# DELETE
curl -X DELETE http://localhost:8080/customers/1

# UPDATE
curl -d '{"id": 4,"name": "Updated"}' -H "Content-Type: application/json" -X PATCH http://localhost:8080/customers/4
```

## 배운점

- given 절에서 willThrow 사용 시 Controller Advice 동작 제대로 안함 -> willAnswer 사용 필요
- verify 사용 시 객체를 넣으면 값 비교가 가능함 (자바에서는 메모리 주소로 반환됐음)
