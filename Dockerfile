# 1. 빌드 스테이지
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# 빌드 효율을 위해 소스 복사 전 실행 권한 및 의존성 캐싱을 고려할 수 있지만, 
# 현재는 단순하게 전체 복사 후 빌드를 진행한다.
COPY . .

# gradlew 파일에 실행 권한 부여 및 빌드
RUN chmod +x ./gradlew
# plain jar 생성을 방지하고 실행 가능한 jar만 생성하도록 실행
RUN ./gradlew clean bootJar

# 2. 실행 스테이지
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# 빌드 스테이지에서 생성된 실행 가능한 jar 파일만 복사한다.
# 보통 bootJar로 생성된 파일은 하나이므로 아래와 같이 복사한다.
COPY --from=build /app/build/libs/*.jar app.jar

# 8080 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
