# springboot-oauth2-security
springboot oauth2 소셜로그인

## Stack
- Java 17.0.7
- Spring Boot 3.2.0
- Spring Data Jpa
- MySQL 8.0.33

## Envorinment Variable

```yaml
spring:
  datasource:
    url: `database_url`
    username: `database_username`
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: create
    database: mysql
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect
        format_sql: true
        show_sql: true
      database-platform: org.hibernate.MySQLDialect
  mail:
    host: `smtp host`
    port: `smtp port`
    username: `smtp id`
    password: `smtp password`
    properties:
      debug: true
      mail:
        smtp:
        auth: true
        ssl.enable: true
        ssl.trust: `smtp host`
        starttls.enable: true
  data:
    redis:
      host: `redis host`
      port: `redis port`

security:
  jwt:
    secret: `jwt secret`
    access:
      expiration: `access token expiration`
    refresh:
      expiration: `refresh token expiration`
  oauth2:
    google:
      authentication_url: `authentication url`
      token_url: `google oauth2 token url`
      user_info_url: `google oauth2 userinfo url`
      client_id: `google client id`
      client_secret: `google client secret`
      redirect_uri: `google oauth2 redirect uri`
```
