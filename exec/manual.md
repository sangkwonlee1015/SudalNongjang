# 포팅 매뉴얼

## 개발 환경

### 프론트

- Nodejs v16.16.0
- npm v8.11.0
- react v18.2.0

### 백

- JDK 1.8
- SpringBoot v2.4.5
- IntelliJ IDEA
- MySQL v8.0.30
- web3j v4.8.7

## 빌드 및 배포 가이드

### 빌드 및 배포 시 특이사항

- 벡엔드 빌드 및 배포 진행 전 로컬 등에 Redis 설치 및 연결 필요
- 프론트 배포에 NGINX 사용 시 SSL 적용 필요

<br>

1. 해당 Gitlab 레포의 master 브랜치를 clone
    ```bash
    git clone https://lab.ssafy.com/s07-blockchain-nft-sub2/S07P22A606.git
    ```

### 백엔드 빌드 및 배포 진행


2. 프로젝트의 아래 위치로 이동
    ```bash
    cd S07P22A606/Backend/a606/a606/
    ```
3. 백엔드 빌드 진행
    ```bash
    chmod +x gradlew
    ./gradlew --debug build
    ```
4. 빌드 결과물 디렉토리로 이동
    ```bash
    cd S07P22A606/Backend/a606/a606/build/libs/
    ```
5. 백엔드 jar 파일 실행하여 백엔드 배포
    ```bash
    sudo nohup java -jar a606-1.0-SNAPSHOT.jar &
    ```

### 프론트엔드 빌드 및 배포 진행
6. 프로젝트의 아래 위치로 이동
    ```bash
    cd S07P22A606/Frontend/
    ```
7. npm 모듈 설치
    ```bash
    npm install
    ```

8. 프론트엔드 실행 (로컬)
    ```bash
    npm start
    ```
9. 프론트엔드 배포 (NGINX를 사용하여 배포하는 경우)
    ```bash
    npm run build
    ```
10. 빌드 결과로 생기는 `/build` 폴더를 NGINX 경로에 위치 및 SSL 설정

## 외부 라이브러리

###  NFT 관련
- SSAFY BlockChain Network 사용
- SSAFY에서 발행한 SSF 토큰 사용
- MetaMask를 이용한 로그인 및 회원가입


## DB dump
- item table만 첨부파일을 확인해주세요
- 나머지는 JPA를 통해 자동으로 생성됩니다




