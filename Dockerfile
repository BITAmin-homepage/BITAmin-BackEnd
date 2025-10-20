# Build Stage (Gradle 빌드)
# -----------------------------
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Runtime Stage (실행용)
# -----------------------------
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-Xms128m", "-Xmx256m", "-jar", "/app/app.jar"]