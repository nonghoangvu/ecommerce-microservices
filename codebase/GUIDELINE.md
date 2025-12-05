## 1. API gateway (Spring Cloud gateway)
- Tạo ___API Gateway___ tại [Spring Initializr](https://start.spring.io/)

![api-gateway-init.png](../gallery/api-gateway-init.png)

- Cấu hình `API gateway`
```yaml
spring:
  config:
    activate:
      on-profile: dev
  devtools:
    add-properties: true
  cloud:
    gateway:
      server:
        webflux:
          globalcors:
            corsConfigurations:
              '[/**]':
                allowedOrigins: "http://localhost:5173"
                allowedHeaders: "*"
                allowedMethods:
                  - GET
                  - POST
                  - PUT
                  - PATCH
                  - DELETE
                  - OPTIONS
          routes:
            - id: authentication-service
              uri: http://${HOST_NAME:localhost}:8081/auth
              predicates:
                - Path=/auth/**, /v3/api-docs/authentication-service
              filters:
                - RewritePath=/auth/(?<segment>.*), /$\{segment}
                - CustomizeFilter
            - id: account-service
              uri: http://${HOST_NAME:localhost}:8082
              predicates:
                - Path=/account/**, /v3/api-docs/account-service
              filters:
                - RewritePath=/account/(?<segment>.*), /$\{segment}
                - CustomizeFilter
```

- Kiểm tra sức khỏe của `API Gateway` bằng command line
```
$ curl --location 'http://localhost:4953/actuator/health'
{
    "status": "UP"
}
```

## 2. Account Service
- Tạo ___API Gateway___ tại [Spring Initializr](https://start.spring.io/)

- ![account-service-init.png](../gallery/account-service-init.png)

## 3. Authentication Service
- Tạo ___API Gateway___ tại [Spring Initializr](https://start.spring.io/)

- ![authentication-service-init.png](../gallery/authentication-service-init.png)

## [Swagger](http://localhost:8081/swagger-ui/index.html)

## 4. docker-compose kafka EC2
```yaml
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    container_name: zookeeper
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    ports:
      - "2181:2181"

  kafka:
    image: confluentinc/cp-kafka:7.5.0
    container_name: kafka
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"     # internal for docker network
      - "29092:29092"   # external for EC2
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181

      # listeners mapping
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092,PLAINTEXT_HOST://0.0.0.0:29092
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092,PLAINTEXT_HOST://<IP_ADDRESS>:<PORT>

      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT

      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1

  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    container_name: kafka-ui
    ports:
      - "9191:8080"
    environment:
      KAFKA_CLUSTERS_0_NAME: local
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka:9092
    depends_on:
      - kafka

networks:
  default:
    name: api-network
```