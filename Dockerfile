FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/spaceships-0.0.1-SNAPSHOT app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]