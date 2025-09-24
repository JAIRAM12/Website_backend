FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/CollageBackend.jar CollageBackend.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "CollageBackend.jar"]