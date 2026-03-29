FROM eclipse-temurin:17-jdk

RUN apt-get update && \
    apt-get install -y ghostscript && \
    apt-get clean
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Xms128m", "-Xmx256m", "-jar", "/app.jar"]