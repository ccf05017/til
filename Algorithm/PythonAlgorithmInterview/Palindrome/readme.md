# 회문 찾기
## 문제
- https://leetcode.com/problems/valid-palindrome/
- 회문 여부를 판단해라
    - 회문: 거꾸로 뒤집어도 같은 단어
    
## 제약조건
- 대소문자를 구분하지 않는다.
- 영문자와 숫자만을 대상으로 한다.

## 입력 예제
- "A man, a plan, a canal: Panama" => True
- "race a car" => false

## 핵심풀이
- isalnum 함수: 영문자, 숫자인지 판단해주는 내장 함수
    - 이거 모르면 정규표현식을 미친듯이 건드렸겠지..
    - 그래도 정규표현식은 익혀두자
