## 1. API gateway (Spring Cloud gateway)
- Tạo ___API Gateway___ tại [Spring Initializr](https://start.spring.io/)

![api-gateway-init.png](gallery/api-gateway-init.png)

- Cấu hình `API gateway` tại `application.yml`
```yml
server:
  port: 4953

spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes: # điều hướng request đến service tương ứng thông qua chỉ định trên url
        - id: authentication-service
          uri: http://localhost:8081
          predicates:
            - Path=/auth/** # chỉ định url có chứa ký tự /auth
          filters:
            - RewritePath=/auth/(?<segment>.*), /$\{segment} # thay thế ký tự /auth/ thành /
        - id: account-service
          uri: http://localhost:8082
          predicates:
            - Path=/account/** # chỉ định url có chứa ký tự /account
          filters:
            - RewritePath=/account/(?<segment>.*), /$\{segment} # thay thế ký tự /account/ thành /

management: # cho phép hiển thị thông tin sức khoẻ của service
  endpoints:
    web:
      exposure:
        include: '*'
```

## 2. Account Service
- Tạo ___API Gateway___ tại [Spring Initializr](https://start.spring.io/)

- ![account-service-init.png](gallery/account-service-init.png)

- Cấu hình `API gateway` tại `application.yml`

```yml
server:
  port: 8082

spring:
  application:
    name: account-service

management: # cho phép hiển thị thông tin sức khoẻ của service
  endpoints:
    web:
      exposure:
        include: '*'
```

## 3. Authentication Service
- Tạo ___API Gateway___ tại [Spring Initializr](https://start.spring.io/)

- ![authentication-service-init.png](gallery/authentication-service-init.png)

- Cấu hình `API gateway` tại `application.yml`

```yml
server:
  port: 8081

spring:
  application:
    name: authentication-service

management: # cho phép hiển thị thông tin sức khoẻ của service
  endpoints:
    web:
      exposure:
        include: '*'
```