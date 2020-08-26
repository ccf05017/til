# 인프런 영한님 QueryDSL 강의
## 1. 설정
- 설정 파일 모두 설정 후 `gradle - tasks - other`의 compileQueryDsl을 실행해서 잘 되는지 확인할 것

## 2. 장점
- 복잡한 쿼리 작성 시 컴파일 에러 단계에서 잘못된 쿼리를 다 잡아준다.
- 자동으로 파라미터 바인딩을 해주기 때문에 성능상의 이점과 SQL 인젝션 방어에 유리하다.
- 쿼리 작성 시 IDE 도움을 받을 수 있다. 

## 3. QType
- 보통 컴파일 시 자동을 생성되기 때문에 그거 사용하면 편하다.
- 직접 선언해주는 방법도 있다. (같은 테이블 조인할 때는 직접 선언해야만 한다.)

## 4. 검색
- 생각하는 웬만한 조건절은 다 들어있다. (contain, in, startWith 등등)

## 5. 결과 조회
- fetch: 리스트 조회. 데이터 없으면 빈 배열 반환
- fetchOne: 단건 조회. 데이터 없으면 null 반환 / 데이터가 둘 이상이면 NonUniqueResultException 발생
- fetchFist: fetchOne에 limit 1을 걸어서 쿼리 실행
- fetchResults: 페이징 정보를 포함해서 결과 반환
    - 성능이 중요한 페이징 처리 시에는 이거 쓰지 말고 아래 fetchCount 쿼리를 따로 만들어서 처리해라
- fetchCount: 단순한 count 쿼리

## 6. 정렬
- 마찬가지로 알고 있는 정렬 앵간한 거 다 된다.
- null인 경우 뒤로 미루고 하는 짓도 가능하다.

## 7. 페이징
- offset, limit을 설정해서 구현할 수 있다.
    - limit: 한번에 불러올 데이터의 양
    - offset: 0부터 시작하며, 건너뛸 페이지의 수
