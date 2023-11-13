# Build Stage
FROM gradle:7.4-jdk11-alpine as builder
WORKDIR /build

# 캐시 최적화를 위해 먼저 gradle 파일 복사
COPY build.gradle settings.gradle /build/
# gradle 파일 변경시에만 의존 패키지 다운로드
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

# 소스 코드를 복사하고 애플리케이션 빌드
COPY src /build/src
RUN gradle build -x test --parallel

# Application Stage
FROM openjdk:11.0-slim
WORKDIR /app

# 빌더 이미지에서 jar 파일만 복사
COPY --from=builder /build/build/libs/*-SNAPSHOT.jar ./app.jar

EXPOSE 8080

# root 대신 nobody 권한으로 실행
USER nobody
ENTRYPOINT ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "-Dsun.net.inetaddr.ttl=0", "app.jar"]
