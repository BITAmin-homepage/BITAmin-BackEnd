FROM openjdk:17-jdk-slim

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Xms128m", "-Xmx256m", "-jar", "/app.jar"]