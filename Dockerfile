FROM openjdk:17-jdk-slim
VOLUME /tmp
RUN mvn clean install
COPY target/spaceships-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]