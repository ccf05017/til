# Spring Cloud Config Server Demo
## 사전 준비
```shell script
# At Project Root Directory
mkdir $HOME/demo-config-repo
cp ./limits-service.yml $HOME/demo-config-repo
cp ./limits-service-dev.yml $HOME/demo-config-repo
cp ./limits-service-qa.yml $HOME/demo-config-repo
cd $HOME/demo-config-repo
git init .
git add -A .
git commit -m "limits-service config"
```

## 확인
- GET http://localhost:8888/limits-service/default
- GET http://localhost:8888/limits-service/dev
- GET http://localhost:8888/limits-service/qa

## 설정 사항
- @EnableConfigServer 설정
- application.yml 설정 파일에 git 경로 설정 및 서버 포트 변경(tcp/8888)
