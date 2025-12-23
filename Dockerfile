# 1. 빌드 스테이지
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# 빌드 효율을 위해 소스 복사
COPY . .

# gradlew 파일에 실행 권한 부여 및 빌드
RUN chmod +x ./gradlew
# bootJar 생성 (-x test를 추가하여 빌드 속도를 높이는 것을 권장한다)
RUN ./gradlew clean bootJar -x test

# 2. 실행 스테이지
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# 빌드 스테이지에서 생성된 jar 파일 복사
# /app/app.jar 경로에 저장된다.
COPY --from=build /app/build/libs/*.jar app.jar

# 8080 포트 노출
EXPOSE 8080

# 애플리케이션 실행
# 기존 /app.jar에서 app.jar로 수정 (WORKDIR가 /app이므로)
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]