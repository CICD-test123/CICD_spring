# 1. 빌드 스테이지
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY . .

# gradlew 파일에 실행 권한 부여 (권한 오류 방지)
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

# 2. 실행 스테이지
# intellij-temurin을 eclipse-temurin으로 수정한다.
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# 빌드 스테이지에서 생성된 jar 파일만 복사한다.
# build/libs 폴더에 여러 개의 jar가 생길 수 있으므로 특정 패턴을 지정하는 것이 좋다.
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]