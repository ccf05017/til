# AxiosRequestScheduler
## 개요
- 반복적으로 API를 요청해야 할 때 사용

## 사용법
```shell script
npm install
node axiosRequestScheduler.js
```

## Custom 설정
- axiosRequestScheduler 파일 내 scheduler 함수 인자 조절하면 됨
- scheduler(delayTime, bulkCount, method, url, data)

## 업데이트 계획
- 커맨드 라인 인자를 받아서 스케쥴러 동작 
