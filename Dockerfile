FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/collageBackend.jar collageBackend.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "collageBackend.jar"]