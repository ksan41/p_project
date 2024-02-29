# 

## 설계

### ERD 설계
![cafe_erd](https://github.com/ksan41/pj_smallCafe/assets/58001871/6d085180-5742-4a7a-a9c4-7fb6d3f68d33)


## 프로젝트 실행

> 💡 docker, docker-compose 가 설치되어 있고 실행 가능한 환경이어야 합니다.
> 

1. 프로젝트 clone
2. clone 받은 shop 폴더로 이동하여 아래 명령어 입력
    
    ```bash
    docker-compose up -d
    ```
    
    입력 시 도커 이미지와 컨테이너 생성 및 실행
    
    - mysql_5 : mysql 5.7 db 컨테이너
    - 생성 시 ddl 자동 실행하여 shop 데이터베이스 내 테이블 생성
    - application : 프로젝트가 실행되는 컨테이너
    - 프로젝트 실행 시 resources 의 data.sql 내에 있는 테스트용 데이터 자동 등록

## API 설명 & 테스트 방법

### 관리자(manager)

1. 회원가입

http://localhost:8080/manager/join

method: POST

body: 

```json
{
    "phone": "01012345679",
    "password": "12345678",
    "name": "홍길순"
}
```

2. 로그인

http://localhost:8080/manager/login

method: POST

body: 

```json
{
    "phone": "01012345678",
    "password": "12345678"
}
```

로그인 성공 시 아래와 같은 응답(body) 반환 및 쿠키 생성

- data : Access 토큰이 담김. 이후 로그인이 필요한 요청 시 request header에 Access 토큰을 담아 요청
- Cookie: X-AUTH-COOKIE 라는 이름으로 Refresh 토큰이 담김

```json
{
    "meta": {
        "code": 200,
        "message": "ok"
    },
    "data": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwMTAxMjM0NTY3OCIsImlhdCI6MTcwNzcyMDIyMiwiZXhwIjoxNzA3NzIzODIyfQ.ISmLjzJ6gQqWO1Ovr7N5nfNJsT4MY2cMX4leO03Jk54"
}
```
![Untitled](https://github.com/ksan41/p_project/assets/58001871/a9ae53b0-6281-438a-93f7-d645e6e3e274)

---

### 💡 로그인 이후 api 요청

request Headers에 다음과 같이 헤더를 추가하여 요청

- key: X-AUTH-TOKEN
- value: 로그인하여 받은 data의 토큰(엑세스 토큰)

![Untitled 1](https://github.com/ksan41/p_project/assets/58001871/8a515fa3-8f07-4901-a486-828b795611e1)


3. 로그아웃

[http://localhost:8080/manager/lo](http://localhost:8080/manager/login)gout

method: POST

### 상품(product)

> 💡 등록 이외의 테스트는 테스트 데이터를 활용합니다.
위의 ‘로그인’의 예시에 있는 정보로 로그인을 하고,
’로그인 이후 api 요청’ 과 같은 방식으로 헤더를 설정하여 테스트합니다.
> 

1. 상품 등록

상품을 등록합니다.
현재 로그인되어있는 관리자의 상품으로 등록됩니다.

http://localhost:8080/product/add

method: POST

body:

```json
{
    "price": 5000,
    "costPrice": 2000,
    "category": "음료",
    "productName": "복숭아주스",
    "size": "large"
}
```

2. 상품 수정

상품을 수정합니다.

현재 로그인되어있는 관리자의 상품만 수정이 가능합니다.

[http://localhost:8080/product/mod/](http://localhost:8080/product/mod/2)1

method: PUT

body:

```json
{
    "price": 8000,
    "costPrice": 3000,
    "category": "음료",
    "productName": "자몽에이드",
    "size": "small",
    "discription": "맛있습니다."
}
```

3. 상품 삭제

상품을 삭제합니다.

현재 로그인되어있는 관리자의 상품만 삭제 가능합니다.

[http://localhost:8080/product/remove/](http://localhost:8080/product/remove/2)1

method: DELETE

4. 상품 검색

검색 키워드와 페이지를 지정하여 여러개의 상품을 조회합니다.
키워드 입력없이 전체 조회도 가능하며, 자음 검색이 가능합니다. 예) ㅇㅁ -> 아메리카노 ㅇ -> 아메리카노, 아이스티, 유자차...
현재 로그인되어있는 관리자의 상품만 조회 가능합니다.

http://localhost:8080/product/search

method: GET

params:

- 키워드
    - keyword: 조회할 키워드 입력
- 페이지
    - page : 조회할 페이지 번호(0부터 시작)

![Untitled 2](https://github.com/ksan41/p_project/assets/58001871/b5e2015f-9ec7-4f85-9288-d21f6b2d1c0f)


5. 상품 조회

상품 정보를 한개 조회합니다.
현재 로그인되어있는 관리자의 상품만 조회 가능합니다.

http://localhost:8080/product/get/2

method: GET
