# toby-spring-example

#### Environment
Java 1.8  
Spring Boot 2.1.4  
MySQL 8.0.15

mysql 접속이 잘 안 되면..
`mysql.server restart`

mysql 접속하기
`mysql -u root -p`  
password: password

### Note
- Java 11부터는 JAXB가 내장되어있지 않기 때문에 이전 버전을 써야 XML 부분 실습이 가능

#### SQL
1장
```SQL
CREATE DATABASE TOBYSPRING;

USE TOBYSPRING;

CREATE TABLE USERS (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(20) NOT NULL, 
    password VARCHAR(10) NOT NULL
);

USE TESTDB;

CREATE TABLE USERS (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(20) NOT NULL, 
    password VARCHAR(10) NOT NULL
);
```


5장
```SQL
USE TESTDB;

ALTER TABLE USERS ADD level TINYINT NOT NULL;
ALTER TABLE USERS ADD login INT NOT NULL;
ALTER TABLE USERS ADD recommend INT NOT NULL;  
ALTER TABLE USERS ADD email varchar(30);
```

SMTP: GMAIL => 2단계 인증 password를 {2nd-factor-password}에 설정  